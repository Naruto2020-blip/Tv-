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
        val currentTotal = hour * 60 + minute
        val startTotal = startHour * 60 + startMinute
        val endTotal = if (endHour < startHour || (endHour == startHour && endMinute < startMinute)) {
            (endHour + 24) * 60 + endMinute
        } else {
            endHour * 60 + endMinute
        }

        val adjustedCurrent = if (endTotal >= 24 * 60 && currentTotal < startTotal) {
            currentTotal + 24 * 60
        } else {
            currentTotal
        }

        return adjustedCurrent in startTotal until endTotal
    }

    fun getProgress(hour: Int, minute: Int): Float {
        val currentTotal = hour * 60 + minute
        val startTotal = startHour * 60 + startMinute
        val endTotal = if (endHour < startHour || (endHour == startHour && endMinute < startMinute)) {
            (endHour + 24) * 60 + endMinute
        } else {
            endHour * 60 + endMinute
        }

        val adjustedCurrent = if (endTotal >= 24 * 60 && currentTotal < startTotal) {
            currentTotal + 24 * 60
        } else {
            currentTotal
        }

        if (adjustedCurrent < startTotal) return 0f
        if (adjustedCurrent >= endTotal) return 1f
        val duration = endTotal - startTotal
        if (duration <= 0) return 0f
        return (adjustedCurrent - startTotal).toFloat() / duration.toFloat()
    }

    fun remainingMinutes(hour: Int, minute: Int): Int {
        val currentTotal = hour * 60 + minute
        val startTotal = startHour * 60 + startMinute
        val endTotal = if (endHour < startHour || (endHour == startHour && endMinute < startMinute)) {
            (endHour + 24) * 60 + endMinute
        } else {
            endHour * 60 + endMinute
        }

        val adjustedCurrent = if (endTotal >= 24 * 60 && currentTotal < startTotal) {
            currentTotal + 24 * 60
        } else {
            currentTotal
        }

        return (endTotal - adjustedCurrent).coerceAtLeast(0)
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
