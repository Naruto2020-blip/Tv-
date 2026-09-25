package com.example.data.epg

import android.util.Log
import com.example.data.model.TvProgram
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.regex.Pattern

/**
 * Live scraper and parser for AmericaTVGuide Costa Rica (americatvguide.com).
 * Fetches real-time, verified broadcast schedules for Costa Rican national television channels.
 */
object AmericaTvGuideScraper {

    private const val TAG = "AmericaTvGuideScraper"
    private const val BASE_URL = "https://americatvguide.com/en/costa-rica/c/"

    /**
     * Mapping from our local channel ID to the channel slug on americatvguide.com
     */
    val channelSlugMap: Map<String, String> = mapOf(
        "teletica7" to "teletica",
        "canal6repretel" to "canal-6-repretel",
        "canal11repretel" to "canal-11-repretel",
        "canal4repretel" to "canal-4-repretel",
        "canal8multimedios" to "multimedios",
        "canal13sinart" to "sinart",
        "extratv42" to "extra-tv-42",
        "futv" to "futv",
        "canal14sancarlos" to "tvn-14",
        "vmlatino" to "vm-latino",
        "canal1cr" to "canal-1",
        "sanjosetv" to "san-jose-tv",
        "cristovision31" to "cristovision",
        "enlacejuvenil" to "enlace-juvenil"
    )

    private val PROGRAM_PATTERN = Pattern.compile("title=\"([0-2][0-9]):([0-5][0-9])\\s+([^\"]+)\"")

    /**
     * Fetches all supported Costa Rica channels concurrently from AmericaTVGuide.
     */
    suspend fun fetchAllCostaRicaChannels(httpClient: OkHttpClient): Map<String, List<TvProgram>> = coroutineScope {
        val deferreds = channelSlugMap.map { (channelId, slug) ->
            async(Dispatchers.IO) {
                try {
                    val programs = fetchSingleChannel(httpClient, channelId, slug)
                    if (programs.isNotEmpty()) {
                        Log.d(TAG, "Successfully scraped ${programs.size} programs for $channelId from AmericaTVGuide")
                        channelId to programs
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "Failed to scrape $channelId ($slug): ${e.message}")
                    null
                }
            }
        }

        deferreds.awaitAll().filterNotNull().toMap()
    }

    /**
     * Fetches a single channel's daily schedule from AmericaTVGuide.
     */
    fun fetchSingleChannel(httpClient: OkHttpClient, channelId: String, slug: String): List<TvProgram> {
        val url = "$BASE_URL$slug"
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Mobile Safari/537.36")
            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .header("Accept-Language", "es-CR,es;q=0.9,en;q=0.8")
            .build()

        val response = httpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            response.close()
            return emptyList()
        }

        val html = response.body?.string() ?: ""
        response.close()

        return parseChannelHtml(html, channelId)
    }

    /**
     * Parses the HTML table containing the channel's daily schedule.
     */
    fun parseChannelHtml(html: String, channelId: String): List<TvProgram> {
        if (html.isBlank()) return emptyList()

        // Extract first table (Today's schedule)
        val tableStartIndex = html.indexOf("<table class=\"table table-striped table-sm\">")
        val tableHtml = if (tableStartIndex != -1) {
            val tableEndIndex = html.indexOf("</table>", tableStartIndex)
            if (tableEndIndex != -1) {
                html.substring(tableStartIndex, tableEndIndex)
            } else {
                html.substring(tableStartIndex)
            }
        } else {
            html
        }

        val matcher = PROGRAM_PATTERN.matcher(tableHtml)
        val rawList = mutableListOf<Triple<Int, Int, String>>()

        while (matcher.find()) {
            val hour = matcher.group(1)?.toIntOrNull() ?: continue
            val minute = matcher.group(2)?.toIntOrNull() ?: continue
            val rawTitle = matcher.group(3) ?: continue
            val cleanedTitle = cleanTitle(rawTitle)
            if (cleanedTitle.isNotBlank()) {
                rawList.add(Triple(hour, minute, cleanedTitle))
            }
        }

        if (rawList.isEmpty()) return emptyList()

        val programs = mutableListOf<TvProgram>()
        for (i in rawList.indices) {
            val (startH, startM, title) = rawList[i]
            val (endH, endM) = if (i + 1 < rawList.size) {
                val nextStartH = rawList[i + 1].first
                val nextStartM = rawList[i + 1].second
                Pair(nextStartH, nextStartM)
            } else {
                if (startH == 23) Pair(0, 0) else Pair((startH + 1) % 24, startM)
            }

            val category = inferCategory(title)
            val description = generateDescription(title, category)

            programs.add(
                TvProgram(
                    id = "atvg_${channelId}_${startH}_${startM}",
                    channelId = channelId,
                    title = title,
                    description = description,
                    startHour = startH,
                    startMinute = startM,
                    endHour = endH,
                    endMinute = endM,
                    category = category
                )
            )
        }

        return programs
    }

    private fun cleanTitle(raw: String): String {
        return raw
            .replace("&iexcl;", "¡")
            .replace("&iquest;", "¿")
            .replace("&amp;", "&")
            .replace("&quot;", "\"")
            .replace("&#039;", "'")
            .replace("&apos;", "'")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&aacute;", "á")
            .replace("&eacute;", "é")
            .replace("&iacute;", "í")
            .replace("&oacute;", "ó")
            .replace("&uacute;", "ú")
            .replace("&ntilde;", "ñ")
            .replace("&Ntilde;", "Ñ")
            .replace("Â¡", "¡")
            .replace("Â¿", "¿")
            .replace("Ã¡", "á")
            .replace("Ã©", "é")
            .replace("Ã­", "í")
            .replace("Ã³", "ó")
            .replace("Ãº", "ú")
            .replace("Ã±", "ñ")
            .replace("Ã‘", "Ñ")
            .trim()
    }

    private fun inferCategory(title: String): String {
        val lower = title.lowercase()
        return when {
            lower.contains("noticia") || lower.contains("telediario") || lower.contains("telenoticias") ||
                    lower.contains("informa") || lower.contains("edición") || lower.contains("alerta") -> "Noticias"

            lower.contains("fútbol") || lower.contains("futbol") || lower.contains("deport") ||
                    lower.contains("liga") || lower.contains("juego") || lower.contains("futsal") ||
                    lower.contains("titulares") -> "Deportes"

            lower.contains("novela") || lower.contains("bahar") || lower.contains("rosa de guadalupe") ||
                    lower.contains("como dice el dicho") || lower.contains("leyla") || lower.contains("fruto prohibido") ||
                    lower.contains("hijas de") -> "Novela"

            lower.contains("cine") || lower.contains("película") || lower.contains("pelicula") ||
                    lower.contains("movie") || lower.contains("film") -> "Cine"

            lower.contains("serie") || lower.contains("ncis") || lower.contains("walking dead") ||
                    lower.contains("velvet") || lower.contains("starsky") || lower.contains("chavo") ||
                    lower.contains("chapulín") || lower.contains("chapulin") -> "Series"

            lower.contains("música") || lower.contains("musica") || lower.contains("video") ||
                    lower.contains("hits") || lower.contains("pop") || lower.contains("mix") ||
                    lower.contains("rock") || lower.contains("beats") -> "Música"

            lower.contains("infantil") || lower.contains("animad") || lower.contains("caricatura") ||
                    lower.contains("zamba") -> "Infantil"

            lower.contains("misa") || lower.contains("rosario") || lower.contains("oración") ||
                    lower.contains("oracion") || lower.contains("ángelus") || lower.contains("angelus") ||
                    lower.contains("dios") || lower.contains("pastor") || lower.contains("católica") ||
                    lower.contains("catolica") || lower.contains("coronilla") -> "Religión"

            lower.contains("documental") || lower.contains("cultura") || lower.contains("ciencia") ||
                    lower.contains("historia") || lower.contains("arte") -> "Cultura"

            lower.contains("buen día") || lower.contains("buen dia") || lower.contains("giros") ||
                    lower.contains("de boca en boca") || lower.contains("qué buena tarde") ||
                    lower.contains("que buena tarde") || lower.contains("más que noticias") ||
                    lower.contains("mas que noticias") || lower.contains("calle 7") ||
                    lower.contains("el gordo y la flaca") || lower.contains("despierta") -> "Revista"

            else -> "General"
        }
    }

    private fun generateDescription(title: String, category: String): String {
        return ""
    }
}
