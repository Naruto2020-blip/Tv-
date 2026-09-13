package com.example.data.model

import java.util.Locale

data class TvProgram(
    val id: String,
    val channelId: String,
    val title: String,
    val description: String,
    val startHour: Int,
    val startMinute: Int,
    val endHour: Int,
    val endMinute: Int,
    val category: String = "General",
    val rating: String = "TP" // Todo Público
) {
    fun isLiveAt(hour: Int, minute: Int): Boolean {
        val currentMins = hour * 60 + minute
        val startMins = startHour * 60 + startMinute
        val endMins = endHour * 60 + endMinute

        return if (startMins < endMins) {
            // Normal program during the same calendar day (e.g. 08:00 to 10:00)
            currentMins in startMins until endMins
        } else {
            // Program crosses midnight (e.g. 22:30 to 06:00, or 23:00 to 00:00)
            currentMins >= startMins || (endMins > 0 && currentMins < endMins)
        }
    }

    fun getProgress(hour: Int, minute: Int): Float {
        val currentMins = hour * 60 + minute
        val startMins = startHour * 60 + startMinute
        val endMins = endHour * 60 + endMinute

        val (duration, elapsed) = if (startMins < endMins) {
            val dur = endMins - startMins
            val el = currentMins - startMins
            Pair(dur, el)
        } else {
            val totalEnd = if (endMins == 0) 1440 else endMins + 1440
            val dur = totalEnd - startMins
            val el = if (currentMins < startMins) (currentMins + 1440) - startMins else currentMins - startMins
            Pair(dur, el)
        }

        if (duration <= 0) return 0f
        return (elapsed.toFloat() / duration.toFloat()).coerceIn(0f, 1f)
    }

    fun remainingMinutes(hour: Int, minute: Int): Int {
        val currentMins = hour * 60 + minute
        val startMins = startHour * 60 + startMinute
        val endMins = endHour * 60 + endMinute

        val (duration, elapsed) = if (startMins < endMins) {
            val dur = endMins - startMins
            val el = currentMins - startMins
            Pair(dur, el)
        } else {
            val totalEnd = if (endMins == 0) 1440 else endMins + 1440
            val dur = totalEnd - startMins
            val el = if (currentMins < startMins) (currentMins + 1440) - startMins else currentMins - startMins
            Pair(dur, el)
        }

        return (duration - elapsed).coerceAtLeast(0)
    }

    val timeSpanFormatted: String
        get() = String.format(
            Locale.US,
            "%02d:%02d - %02d:%02d",
            startHour,
            startMinute,
            endHour,
            endMinute
        )

    val startTimeFormatted: String
        get() = String.format(Locale.US, "%02d:%02d", startHour, startMinute)
}
