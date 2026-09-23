package com.example.data.epg

import android.util.Log
import com.example.data.model.TvProgram
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.Calendar
import java.util.TimeZone
import java.util.regex.Pattern

/**
 * Live scraper and parser for FUTV Costa Rica official programming page:
 * https://futvcr.com/programacion/
 *
 * Extracts the official, verified weekly programming schedule published by FUTV.
 */
object FutvScraper {

    private const val TAG = "FutvScraper"
    const val URL = "https://futvcr.com/programacion/"
    private val crTimeZone = TimeZone.getTimeZone("America/Costa_Rica")

    private val ROW_PATTERN = Pattern.compile("<tr[^>]*>(.*?)</tr>", Pattern.DOTALL)
    private val CELL_PATTERN = Pattern.compile("<t[dh][^>]*>(.*?)</t[dh]>", Pattern.DOTALL)
    private val TAG_CLEANER = Pattern.compile("<[^>]+>")

    /**
     * Fetches today's schedule directly from https://futvcr.com/programacion/
     */
    fun fetchFutvSchedule(httpClient: OkHttpClient): List<TvProgram> {
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

            parseFutvSchedule(html)
        } catch (e: Exception) {
            Log.e(TAG, "Error scraping FUTV schedule: ${e.message}")
            emptyList()
        }
    }

    /**
     * Parses the HTML schedule table from futvcr.com/programacion/ for the current day in Costa Rica.
     */
    fun parseFutvSchedule(html: String): List<TvProgram> {
        val nowCr = Calendar.getInstance(crTimeZone)
        val dayOfWeek = nowCr.get(Calendar.DAY_OF_WEEK) // 1=Sun, 2=Mon, 3=Tue, 4=Wed, 5=Thu, 6=Fri, 7=Sat

        // Find table
        val tableStart = html.indexOf("<table")
        if (tableStart == -1) return emptyList()
        val tableEnd = html.indexOf("</table>", tableStart)
        if (tableEnd == -1) return emptyList()

        val tableHtml = html.substring(tableStart, tableEnd + 8)
        val rowMatcher = ROW_PATTERN.matcher(tableHtml)

        var headerRow: List<String>? = null
        val dataRows = mutableListOf<Pair<String, List<String>>>()

        while (rowMatcher.find()) {
            val rowContent = rowMatcher.group(1) ?: continue
            val cellMatcher = CELL_PATTERN.matcher(rowContent)
            val cells = mutableListOf<String>()

            while (cellMatcher.find()) {
                val rawCell = cellMatcher.group(1) ?: ""
                val cleanCell = TAG_CLEANER.matcher(rawCell).replaceAll(" ")
                    .replace("&nbsp;", " ")
                    .replace("&amp;", "&")
                    .replace("&#8211;", "-")
                    .replace("&#8230;", "...")
                    .trim()
                cells.add(cleanCell)
            }

            if (cells.isEmpty()) continue

            if (headerRow == null) {
                headerRow = cells
            } else {
                val timeLabel = cells.firstOrNull() ?: ""
                val dayValues = if (cells.size > 1) cells.subList(1, cells.size) else emptyList()
                dataRows.add(Pair(timeLabel, dayValues))
            }
        }

        if (headerRow == null || dataRows.isEmpty()) return emptyList()

        // Match column index for today (1-based relative to dayValues: 0=Mon, 1=Tue, 2=Wed, 3=Thu, 4=Fri, 5=Sat, 6=Sun)
        val targetColIndex = when (dayOfWeek) {
            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            Calendar.SATURDAY -> 5
            Calendar.SUNDAY -> 6
            else -> 0
        }

        val rawSlots = mutableListOf<RawScheduleSlot>()
        var lastTitle = ""

        for (row in dataRows) {
            val timeStr = row.first
            val programTitle = row.second.getOrNull(targetColIndex)?.trim() ?: ""

            val titleToUse = if (programTitle.isNotEmpty()) {
                lastTitle = programTitle
                programTitle
            } else if (lastTitle.isNotEmpty()) {
                lastTitle
            } else {
                "Fútbol y Deportes FUTV"
            }

            val parsedTime = parseTimeStr(timeStr)
            if (parsedTime != null) {
                rawSlots.add(RawScheduleSlot(parsedTime.first, parsedTime.second, titleToUse))
            }
        }

        if (rawSlots.isEmpty()) return emptyList()

        // Group consecutive slots with the same program title to create clean continuous blocks
        val programs = mutableListOf<TvProgram>()
        var currentSlot = rawSlots.first()
        var currentEndHour = (currentSlot.hour + (if (currentSlot.minute + 30 >= 60) 1 else 0)) % 24
        var currentEndMin = (currentSlot.minute + 30) % 60

        for (i in 1 until rawSlots.size) {
            val nextSlot = rawSlots[i]
            if (nextSlot.title.equals(currentSlot.title, ignoreCase = true)) {
                // Extend duration
                currentEndHour = (nextSlot.hour + (if (nextSlot.minute + 30 >= 60) 1 else 0)) % 24
                currentEndMin = (nextSlot.minute + 30) % 60
            } else {
                programs.add(
                    buildTvProgram(
                        index = programs.size,
                        title = currentSlot.title,
                        startHour = currentSlot.hour,
                        startMin = currentSlot.minute,
                        endHour = currentEndHour,
                        endMin = currentEndMin
                    )
                )
                currentSlot = nextSlot
                currentEndHour = (nextSlot.hour + (if (nextSlot.minute + 30 >= 60) 1 else 0)) % 24
                currentEndMin = (nextSlot.minute + 30) % 60
            }
        }

        // Add last program
        programs.add(
            buildTvProgram(
                index = programs.size,
                title = currentSlot.title,
                startHour = currentSlot.hour,
                startMin = currentSlot.minute,
                endHour = currentEndHour,
                endMin = currentEndMin
            )
        )

        return programs
    }

    private data class RawScheduleSlot(val hour: Int, val minute: Int, val title: String)

    private fun parseTimeStr(timeStr: String): Pair<Int, Int>? {
        val clean = timeStr.trim().lowercase()
        val parts = clean.split(":")
        if (parts.size >= 2) {
            val hour = parts[0].trim().toIntOrNull() ?: return null
            val min = parts[1].replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
            return Pair(hour, min)
        }
        return null
    }

    private fun buildTvProgram(
        index: Int,
        title: String,
        startHour: Int,
        startMin: Int,
        endHour: Int,
        endMin: Int
    ): TvProgram {
        val desc = when {
            title.contains("JUEGO:", ignoreCase = true) || title.contains("PARTIDO", ignoreCase = true) ->
                "Transmisión oficial de fútbol costarricense de la Liga Promerica."
            title.contains("FUTSAL", ignoreCase = true) ->
                "Campeonato nacional de fútbol sala costarricense en directo."
            title.contains("LIGA FEMENINA", ignoreCase = true) ->
                "Fútbol femenino costarricense de primera división."
            title.contains("U20", ignoreCase = true) ->
                "Torneo de ligas menores y prospectos del fútbol nacional."
            title.contains("LA JORNADA", ignoreCase = true) ->
                "Información más relevante de la jornada de la Primera División y Liga de Ascenso."
            title.contains("A FONDO CON", ignoreCase = true) ->
                "Entrevista a fondo con personajes del fútbol nacional e internacional."
            title.contains("LOS SAPRISSA", ignoreCase = true) ->
                "Contenido exclusivo e íntimo del Deportivo Saprissa."
            title.contains("SOMOS EL TEAM", ignoreCase = true) ->
                "El programa oficial del Club Sport Herediano."
            title.contains("TIEMPO FINAL", ignoreCase = true) ->
                "El resumen post jornada más amplio con todo el acontecer nacional e internacional."
            else -> "Transmisión y análisis de fútbol en el canal oficial de Costa Rica."
        }

        return TvProgram(
            id = "futv_live_$index",
            channelId = "futv",
            title = title,
            description = desc,
            startHour = startHour,
            startMinute = startMin,
            endHour = endHour,
            endMinute = endMin,
            category = "Deportes"
        )
    }
}
