package com.example.data.epg

import android.util.Xml
import com.example.data.model.TvProgram
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Fast streaming XMLTV parser optimized for Android.
 * Streams XML elements with minimal memory footprint.
 */
object XmlTvParser {

    private val crTimeZone = TimeZone.getTimeZone("America/Costa_Rica")

    fun parse(inputStream: InputStream): Map<String, List<TvProgram>> {
        val parser = Xml.newPullParser()
        parser.setInput(inputStream, "UTF-8")

        // Maps raw XML channel ID to local app channel ID
        val rawIdToLocalId = mutableMapOf<String, String>()
        val programsByChannel = mutableMapOf<String, MutableList<TvProgram>>()

        var eventType = parser.eventType
        var currentRawChannelId: String? = null
        var currentDisplayName: String? = null

        // Program state
        var progRawChannel: String? = null
        var progStartStr: String? = null
        var progStopStr: String? = null
        var progTitle: String? = null
        var progDesc: String? = null
        var progCategory: String? = null

        // Precalculate today in Costa Rica
        val crNow = Calendar.getInstance(crTimeZone)
        val todayStartMillis = Calendar.getInstance(crTimeZone).apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis - (4 * 3600 * 1000) // Include late night from previous day

        val todayCalendar = Calendar.getInstance(crTimeZone)
        val todayYear = todayCalendar.get(Calendar.YEAR)
        val todayDayOfYear = todayCalendar.get(Calendar.DAY_OF_YEAR)

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "channel" -> {
                            currentRawChannelId = parser.getAttributeValue(null, "id")
                            currentDisplayName = null
                        }
                        "display-name" -> {
                            if (currentRawChannelId != null) {
                                currentDisplayName = parser.nextText()
                                val matchedLocalId = matchChannelId(currentRawChannelId, currentDisplayName)
                                if (matchedLocalId != null) {
                                    rawIdToLocalId[currentRawChannelId] = matchedLocalId
                                }
                            }
                        }
                        "programme" -> {
                            progRawChannel = parser.getAttributeValue(null, "channel")
                            progStartStr = parser.getAttributeValue(null, "start")
                            progStopStr = parser.getAttributeValue(null, "stop")
                            progTitle = null
                            progDesc = null
                            progCategory = null

                            // If we don't recognize the channel ID yet, try to match it directly
                            if (progRawChannel != null && !rawIdToLocalId.containsKey(progRawChannel)) {
                                val matched = matchChannelId(progRawChannel, null)
                                if (matched != null) {
                                    rawIdToLocalId[progRawChannel] = matched
                                }
                            }
                        }
                        "title" -> {
                            if (progRawChannel != null && rawIdToLocalId.containsKey(progRawChannel)) {
                                progTitle = parser.nextText()
                            }
                        }
                        "desc" -> {
                            if (progRawChannel != null && rawIdToLocalId.containsKey(progRawChannel)) {
                                progDesc = parser.nextText()
                            }
                        }
                        "category" -> {
                            if (progRawChannel != null && rawIdToLocalId.containsKey(progRawChannel)) {
                                progCategory = parser.nextText()
                            }
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    when (parser.name) {
                        "channel" -> {
                            if (currentRawChannelId != null && !rawIdToLocalId.containsKey(currentRawChannelId)) {
                                val matched = matchChannelId(currentRawChannelId, currentDisplayName)
                                if (matched != null) {
                                    rawIdToLocalId[currentRawChannelId] = matched
                                }
                            }
                            currentRawChannelId = null
                            currentDisplayName = null
                        }
                        "programme" -> {
                            val localChannelId = progRawChannel?.let { rawIdToLocalId[it] }
                            if (localChannelId != null && !progTitle.isNullOrBlank() && !progStartStr.isNullOrBlank()) {
                                val startCal = parseXmlTvDate(progStartStr!!)
                                val stopCal = progStopStr?.let { parseXmlTvDate(it) }

                                if (startCal != null) {
                                    val progYear = startCal.get(Calendar.YEAR)
                                    val progDayOfYear = startCal.get(Calendar.DAY_OF_YEAR)

                                    // Strictly keep programs corresponding to today in Costa Rica time (UTC-6)
                                    if (progYear == todayYear && progDayOfYear == todayDayOfYear) {
                                        val startMillis = startCal.timeInMillis
                                        val endCal = stopCal ?: Calendar.getInstance(crTimeZone).apply {
                                            timeInMillis = startMillis + 3600_000 // default 1 hour
                                        }

                                        val startHour = startCal.get(Calendar.HOUR_OF_DAY)
                                        val startMinute = startCal.get(Calendar.MINUTE)
                                        val endHour = endCal.get(Calendar.HOUR_OF_DAY)
                                        val endMinute = endCal.get(Calendar.MINUTE)

                                        val program = TvProgram(
                                            id = "online_${localChannelId}_${startMillis}",
                                            channelId = localChannelId,
                                            title = progTitle!!.trim(),
                                            description = progDesc?.trim() ?: "Transmisión en vivo y programación habitual.",
                                            startHour = startHour,
                                            startMinute = startMinute,
                                            endHour = endHour,
                                            endMinute = endMinute,
                                            category = progCategory?.trim() ?: "General"
                                        )

                                        programsByChannel.getOrPut(localChannelId) { mutableListOf() }.add(program)
                                    }
                                }
                            }
                            progRawChannel = null
                            progStartStr = null
                            progStopStr = null
                            progTitle = null
                            progDesc = null
                            progCategory = null
                        }
                    }
                }
            }
            eventType = parser.next()
        }

        // Sort programs chronologically for each channel
        return programsByChannel.mapValues { (_, list) ->
            list.distinctBy { "${it.startHour}:${it.startMinute}_${it.title}" }
                .sortedWith(compareBy({ it.startHour }, { it.startMinute }))
        }
    }

    /**
     * Parses standard XMLTV datetime timestamps:
     * Examples:
     * - "20260913190000 -0600"
     * - "20260913190000 +0000"
     * - "20260913190000-0500"
     * - "20260913190000"
     */
    fun parseXmlTvDate(rawStr: String): Calendar? {
        val clean = rawStr.trim()
        if (clean.length < 14) return null

        return try {
            val date: Date? = if (clean.contains(" ")) {
                val format = SimpleDateFormat("yyyyMMddHHmmss Z", Locale.US)
                format.parse(clean)
            } else if (clean.length >= 19 && (clean[14] == '+' || clean[14] == '-')) {
                val normalized = clean.substring(0, 14) + " " + clean.substring(14)
                val format = SimpleDateFormat("yyyyMMddHHmmss Z", Locale.US)
                format.parse(normalized)
            } else {
                val format = SimpleDateFormat("yyyyMMddHHmmss", Locale.US).apply {
                    timeZone = crTimeZone
                }
                format.parse(clean.substring(0, 14))
            }

            if (date != null) {
                Calendar.getInstance(crTimeZone).apply {
                    time = date
                }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Maps raw XMLTV IDs and display names to our local Costa Rican channel identifiers.
     */
    fun matchChannelId(rawId: String, displayName: String?): String? {
        val idLower = rawId.lowercase()
        val nameLower = displayName?.lowercase().orEmpty()
        val combined = "$idLower $nameLower"
        val norm = combined
            .replace(".", " ")
            .replace("-", " ")
            .replace("_", " ")
            .replace("(", " ")
            .replace(")", " ")
            .replace("á", "a")
            .replace("é", "e")
            .replace("í", "i")
            .replace("ó", "o")
            .replace("ú", "u")

        return when {
            // Teletica Canal 7
            norm.contains("teletica") || (norm.contains("canal 7") && (norm.contains("costa") || norm.contains("cr"))) -> "teletica7"

            // Repretel Canal 6
            norm.contains("repretel 6") || (norm.contains("canal 6") && (norm.contains("costa") || norm.contains("cr") || norm.contains("repretel"))) -> "canal6repretel"

            // Repretel Canal 11
            norm.contains("repretel 11") || (norm.contains("canal 11") && (norm.contains("costa") || norm.contains("cr") || norm.contains("repretel"))) -> "canal11repretel"

            // Repretel Canal 4
            norm.contains("repretel 4") || (norm.contains("canal 4") && (norm.contains("costa") || norm.contains("cr") || norm.contains("repretel"))) -> "canal4repretel"

            // Multimedios Canal 8
            norm.contains("multimedios") || (norm.contains("canal 8") && (norm.contains("costa") || norm.contains("cr"))) -> "canal8multimedios"

            // Trece Costa Rica / SINART / Canal 13
            norm.contains("sinart") || norm.contains("trece") || (norm.contains("canal 13") && (norm.contains("costa") || norm.contains("cr"))) -> "canal13sinart"

            // Extra TV 42
            norm.contains("extra tv") || (norm.contains("canal 42") && (norm.contains("costa") || norm.contains("cr") || norm.contains("extra"))) -> "extratv42"

            // FUTV
            norm.contains("futv") -> "futv"

            // OPA Canal 38
            norm.contains("opa") || (norm.contains("canal 38") && (norm.contains("costa") || norm.contains("cr"))) -> "opacanal38"

            // Canal 1 Costa Rica
            (norm.contains("canal 1") || norm.contains("canal1")) && (norm.contains("costa") || norm.contains("cr") || norm.startsWith("canal1")) -> "canal1cr"

            // TV Norte San Carlos Canal 14
            (norm.contains("canal 14") || norm.contains("tv norte") || norm.contains("tvn")) && norm.contains("carlos") -> "canal14sancarlos"

            // Coto Brus TV
            norm.contains("coto brus") -> "cotobrustv"

            // 88 Stereo TV
            norm.contains("88 stereo") || norm.contains("88stereo") -> "88stereotv"

            // VM Latino
            norm.contains("vm latino") || norm.contains("vmlatino") -> "vmlatino"

            // San José TV
            norm.contains("san jose tv") || norm.contains("sanjosetv") -> "sanjosetv"

            // Cristovisión
            norm.contains("cristovision") -> "cristovision31"

            // Enlace Juvenil
            norm.contains("enlace juvenil") || norm.contains("enlacejuvenil") -> "enlacejuvenil"

            else -> null
        }
    }
}
