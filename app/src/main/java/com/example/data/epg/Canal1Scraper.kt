package com.example.data.epg

import android.util.Log
import com.example.data.model.TvProgram
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.Calendar
import java.util.TimeZone
import java.util.regex.Pattern

/**
 * Live scraper and parser for Canal 1 Costa Rica official programming page:
 * https://canal1cr.com/programacion/
 *
 * Extracts the official, verified weekly programming schedule published by Canal 1.
 */
object Canal1Scraper {

    private const val TAG = "Canal1Scraper"
    const val URL = "https://canal1cr.com/programacion/"
    private val crTimeZone = TimeZone.getTimeZone("America/Costa_Rica")

    private val ROW_PATTERN = Pattern.compile("<tr[^>]*>(.*?)</tr>", Pattern.DOTALL)
    private val CELL_PATTERN = Pattern.compile("<t[dh][^>]*>(.*?)</t[dh]>", Pattern.DOTALL)
    private val TAG_CLEANER = Pattern.compile("<[^>]+>")

    /**
     * Fetches today's schedule directly from https://canal1cr.com/programacion/
     */
    fun fetchCanal1Schedule(httpClient: OkHttpClient): List<TvProgram> {
        return try {
            val request = Request.Builder()
                .url(URL)
                .header("User-Agent", "Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Mobile Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Language", "es-CR,es;q=0.9,en;q=0.8")
                .build()

            val response = httpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                Log.w(TAG, "Failed to load $URL: HTTP ${response.code}")
                response.close()
                return emptyList()
            }

            val html = response.body?.string() ?: ""
            response.close()

            parseCanal1Schedule(html)
        } catch (e: Exception) {
            Log.e(TAG, "Error scraping Canal 1 schedule: ${e.message}")
            emptyList()
        }
    }

    /**
     * Parses the HTML schedule table from canal1cr.com/programacion/ for the current day of the week in Costa Rica.
     */
    fun parseCanal1Schedule(html: String): List<TvProgram> {
        val nowCr = Calendar.getInstance(crTimeZone)
        val dayOfWeek = nowCr.get(Calendar.DAY_OF_WEEK)
        // In Calendar: SUNDAY=1, MONDAY=2, TUESDAY=3, WEDNESDAY=4, THURSDAY=5, FRIDAY=6, SATURDAY=7
        // In the Canal 1 table:
        // Col 0: Inicio, Col 1: Fin
        // Col 2: Lunes (Monday = 2)
        // Col 3: Martes (Tuesday = 3)
        // Col 4: Miércoles (Wednesday = 4)
        // Col 5: Jueves (Thursday = 5)
        // Col 6: Viernes (Friday = 6)
        // Col 7: Sábado (Saturday = 7)
        // Col 8: Domingo (Sunday = 1)
        val targetCol = when (dayOfWeek) {
            Calendar.MONDAY -> 2
            Calendar.TUESDAY -> 3
            Calendar.WEDNESDAY -> 4
            Calendar.THURSDAY -> 5
            Calendar.FRIDAY -> 6
            Calendar.SATURDAY -> 7
            Calendar.SUNDAY -> 8
            else -> 2
        }

        return parseCanal1ScheduleForColumn(html, targetCol)
    }

    /**
     * Parses the schedule for a specific column index from the HTML table.
     */
    fun parseCanal1ScheduleForColumn(html: String, targetCol: Int): List<TvProgram> {
        val tableStartIndex = html.indexOf("<table")
        if (tableStartIndex == -1) return emptyList()
        val tableEndIndex = html.indexOf("</table>", tableStartIndex)
        val tableHtml = if (tableEndIndex != -1) {
            html.substring(tableStartIndex, tableEndIndex + 8)
        } else {
            html.substring(tableStartIndex)
        }

        val rowMatcher = ROW_PATTERN.matcher(tableHtml)
        val rawEntries = mutableListOf<RawEntry>()

        while (rowMatcher.find()) {
            val rowContent = rowMatcher.group(1) ?: continue
            val cellMatcher = CELL_PATTERN.matcher(rowContent)
            val cells = mutableListOf<String>()
            while (cellMatcher.find()) {
                val rawCell = cellMatcher.group(1) ?: ""
                val cleanCell = TAG_CLEANER.matcher(rawCell).replaceAll("").trim()
                cells.add(cleanCell)
            }

            if (cells.size >= 9 && cells[0].contains(":") && cells[1].contains(":")) {
                val startParts = cells[0].split(":")
                val endParts = cells[1].split(":")
                val startH = startParts.getOrNull(0)?.toIntOrNull()
                val startM = startParts.getOrNull(1)?.toIntOrNull()
                val endH = endParts.getOrNull(0)?.toIntOrNull()
                val endM = endParts.getOrNull(1)?.toIntOrNull()

                val rawTitle = cells.getOrNull(targetCol)
                if (startH != null && startM != null && endH != null && endM != null && !rawTitle.isNullOrBlank()) {
                    // Filter out header text if any
                    val lowerTitle = rawTitle.lowercase()
                    if (!lowerTitle.startsWith("lunes") && !lowerTitle.startsWith("sábado") && !lowerTitle.startsWith("domingo") && !lowerTitle.startsWith("inicio")) {
                        rawEntries.add(RawEntry(startH, startM, endH, endM, rawTitle))
                    }
                }
            }
        }

        if (rawEntries.isEmpty()) return emptyList()

        // Merge contiguous identical entries (e.g. 18:00-18:30 and 18:30-19:00 Primero Noticias)
        val mergedEntries = mutableListOf<RawEntry>()
        for (entry in rawEntries) {
            val last = mergedEntries.lastOrNull()
            if (last != null && last.title.equals(entry.title, ignoreCase = true) &&
                last.endHour == entry.startHour && last.endMinute == entry.startMinute) {
                mergedEntries.removeAt(mergedEntries.size - 1)
                mergedEntries.add(RawEntry(last.startHour, last.startMinute, entry.endHour, entry.endMinute, last.title))
            } else {
                mergedEntries.add(entry)
            }
        }

        return mergedEntries.mapIndexed { index, entry ->
            val title = formatTitle(entry.title)
            val category = inferCategory(title)
            val description = generateDescription(title, category)
            TvProgram(
                id = "c1cr_live_${index}_${entry.startHour}_${entry.startMinute}",
                channelId = "canal1cr",
                title = title,
                description = description,
                startHour = entry.startHour,
                startMinute = entry.startMinute,
                endHour = entry.endHour,
                endMinute = entry.endMinute,
                category = category
            )
        }
    }

    private fun formatTitle(raw: String): String {
        return raw.trim()
            .split(" ")
            .filter { it.isNotBlank() }
            .joinToString(" ") { word ->
                val w = word.lowercase()
                when (w) {
                    "y", "de", "del", "el", "la", "los", "las", "en", "sin", "con" -> w
                    "rt", "cgtn", "jps", "cr" -> word.uppercase()
                    else -> word.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                }
            }
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }

    private fun inferCategory(title: String): String {
        val lower = title.lowercase()
        return when {
            lower.contains("noticia") || lower.contains("cgtn") || lower.contains("rt en") -> "Noticias"
            lower.contains("deporte") || lower.contains("kick off") || lower.contains("futbol") -> "Deportes"
            lower.contains("cine") || lower.contains("película") -> "Cine"
            lower.contains("drama") || lower.contains("novela") -> "Novela"
            lower.contains("serie") -> "Series"
            lower.contains("animado") || lower.contains("infantil") -> "Infantil"
            lower.contains("misa") || lower.contains("oración") || lower.contains("rosario") -> "Religión"
            lower.contains("musical") || lower.contains("hits") || lower.contains("música") ||
                    lower.contains("concierto") || lower.contains("fiesta") -> "Música"
            lower.contains("marcelo castro") -> "Opinión"
            lower.contains("reinventados") -> "Revista"
            lower.contains("sorteo") || lower.contains("jps") -> "Especiales"
            lower.contains("como han pasado") || lower.contains("movilidad") -> "Cultura"
            else -> "Variedades"
        }
    }

    private fun generateDescription(title: String, category: String): String {
        val lower = title.lowercase()
        return when {
            lower.contains("serie retro") -> "Las mejores series clásicas y producciones legendarias de la televisión internacional."
            lower.contains("dramas") -> "Grandes producciones dramáticas y telenovelas internacionales de gran audiencia."
            lower.contains("como han pasado los años") -> "Recorrido nostálgico con historia, música y recuerdos inolvidables con la conducción especial de Canal 1."
            lower.contains("musicales") -> "Los mejores videoclips y música variada para disfrutar."
            lower.contains("misa") -> "Santa Misa y celebración eucarística católica en directo."
            lower.contains("animado retro") -> "Aventuras animadas y dibujos clásicos para disfrutar en familia."
            lower.contains("rt en vivo") -> "Transmisión informativa internacional en directo desde la señal de RT Noticias."
            lower.contains("reinventados") -> "Espacio de inspiración empresarial, innovación, emprendimientos y resiliencia en Costa Rica."
            lower.contains("novela china") -> "Superproducción dramática, romance e historia de gran audiencia internacional."
            lower.contains("primero deportes") -> "Toda la actualidad del deporte nacional e internacional, fútbol costarricense y análisis de fondo."
            lower.contains("primero noticias") -> "Noticiero estelar de Canal 1 con la información más veraz y completa del acontecer costarricense."
            lower.contains("marcelo castro presenta") -> "Entrevistas de fondo, investigación y análisis periodístico con el destacado periodista Marcelo Castro."
            lower.contains("kick off") -> "Mesa de debate futbolero, comentarios picantes y análisis de la jornada del fútbol nacional."
            lower.contains("cgtn en vivo") -> "Noticias mundiales, cultura y geopolítica internacional con la señal en directo de CGTN."
            lower.contains("fiesta la tica") -> "Música bailable, fiesta popular y los ritmos que alegran los fines de semana en Costa Rica."
            lower.contains("cine del 1") -> "Grandes estrenos del séptimo arte, películas de acción, drama y comedia familiar."
            lower.contains("cosas que pasas") -> "Historias curiosas, anécdotas y vivencias cotidianas de nuestro entorno."
            lower.contains("movilidad sin mitos") -> "Espacio educativo sobre seguridad vial, transporte y movilidad ciudadana."
            lower.contains("mas que musica") -> "Especiales musicales, entrevistas y lo más sonado de la escena musical."
            lower.contains("retro hits") -> "Los grandes clásicos del pop, rock y baladas que marcaron generaciones."
            lower.contains("sorteo jps") -> "Transmisión en directo del Sorteo de la Lotería Nacional de la Junta de Protección Social de Costa Rica."
            else -> "Emisión oficial de $title en la grilla de programación de Canal 1 Costa Rica."
        }
    }

    private data class RawEntry(
        val startHour: Int,
        val startMinute: Int,
        val endHour: Int,
        val endMinute: Int,
        val title: String
    )
}
