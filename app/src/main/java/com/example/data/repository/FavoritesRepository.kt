package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoritesRepository(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("cr_tv_favorites_prefs", Context.MODE_PRIVATE)

    private val _favoriteChannelIds = MutableStateFlow<Set<String>>(loadFavorites())
    val favoriteChannelIds: StateFlow<Set<String>> = _favoriteChannelIds.asStateFlow()

    private val _reminderProgramIds = MutableStateFlow<Set<String>>(loadReminders())
    val reminderProgramIds: StateFlow<Set<String>> = _reminderProgramIds.asStateFlow()

    private fun loadFavorites(): Set<String> {
        val stored = prefs.getStringSet(KEY_FAVORITES, null)
        return stored?.toSet() ?: setOf("teletica7", "canal8multimedios", "futv", "canal6repretel")
    }

    private fun loadReminders(): Set<String> {
        return prefs.getStringSet(KEY_REMINDERS, emptySet())?.toSet() ?: emptySet()
    }

    fun toggleFavorite(channelId: String) {
        val current = _favoriteChannelIds.value.toMutableSet()
        if (current.contains(channelId)) {
            current.remove(channelId)
        } else {
            current.add(channelId)
        }
        prefs.edit().putStringSet(KEY_FAVORITES, current).apply()
        _favoriteChannelIds.value = current
    }

    fun isFavorite(channelId: String): Boolean {
        return _favoriteChannelIds.value.contains(channelId)
    }

    fun toggleReminder(programId: String) {
        val current = _reminderProgramIds.value.toMutableSet()
        if (current.contains(programId)) {
            current.remove(programId)
        } else {
            current.add(programId)
        }
        prefs.edit().putStringSet(KEY_REMINDERS, current).apply()
        _reminderProgramIds.value = current
    }

    fun hasReminder(programId: String): Boolean {
        return _reminderProgramIds.value.contains(programId)
    }

    companion object {
        private const val KEY_FAVORITES = "fav_channels"
        private const val KEY_REMINDERS = "program_reminders"
    }
}
