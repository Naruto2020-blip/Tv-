package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.epg.EpgMode
import com.example.data.epg.EpgSyncStatus
import com.example.data.epg.OnlineEpgRepository
import com.example.data.model.ChannelCategory
import com.example.data.model.TvChannel
import com.example.data.model.TvProgram
import com.example.data.repository.CostaRicaChannelsData
import com.example.data.repository.CostaRicaEpgData
import com.example.data.repository.FavoritesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TvViewModel(application: Application) : AndroidViewModel(application) {

    private val favoritesRepository = FavoritesRepository(application)
    val onlineEpgRepository = OnlineEpgRepository(application)

    val epgSyncStatus: StateFlow<EpgSyncStatus> = onlineEpgRepository.syncStatus
    val onlineSchedules: StateFlow<Map<String, List<TvProgram>>> = onlineEpgRepository.onlineSchedules
    val epgMode: StateFlow<EpgMode> = onlineEpgRepository.epgMode

    fun toggleEpgMode() {
        onlineEpgRepository.toggleEpgMode()
    }

    fun setEpgMode(mode: EpgMode) {
        onlineEpgRepository.setEpgMode(mode)
    }

    private val _allChannels = MutableStateFlow(CostaRicaChannelsData.channels)
    val allChannels: StateFlow<List<TvChannel>> = _allChannels

    private val _selectedChannel = MutableStateFlow(CostaRicaChannelsData.channels.first())
    val selectedChannel: StateFlow<TvChannel> = _selectedChannel

    private val _costaRicaTime = MutableStateFlow(CostaRicaEpgData.getCurrentCostaRicaTime())
    val costaRicaTime: StateFlow<Pair<Int, Int>> = _costaRicaTime

    init {
        // Continuous time clock update (every 15s)
        viewModelScope.launch {
            while (true) {
                delay(15_000)
                _costaRicaTime.value = CostaRicaEpgData.getCurrentCostaRicaTime()
            }
        }

        // Automatic background EPG synchronization engine:
        // Automatically syncs on launch and refreshes periodically to keep EPG always updated
        viewModelScope.launch {
            // Initial sync on startup
            onlineEpgRepository.syncEpg(force = false)

            // Recurring automatic sync every 30 minutes
            while (true) {
                delay(30 * 60 * 1000L)
                onlineEpgRepository.syncEpg(force = true)
            }
        }
    }

    private val _selectedCategory = MutableStateFlow(ChannelCategory.TODOS)
    val selectedCategory: StateFlow<ChannelCategory> = _selectedCategory

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab

    private val _isFullscreen = MutableStateFlow(false)
    val isFullscreen: StateFlow<Boolean> = _isFullscreen

    val favoriteChannelIds: StateFlow<Set<String>> = favoritesRepository.favoriteChannelIds

    val reminderProgramIds: StateFlow<Set<String>> = favoritesRepository.reminderProgramIds

    // Filtered channels combining category, search, and favorites
    val displayedChannels: StateFlow<List<TvChannel>> = combine(
        _allChannels,
        _selectedCategory,
        _searchQuery,
        favoriteChannelIds,
        _selectedTab
    ) { channels, category, query, favs, tab ->
        var list = channels

        // If tab is Favorites (tab 3), filter by favs
        if (tab == 3) {
            list = list.filter { favs.contains(it.id) }
        } else if (category != ChannelCategory.TODOS) {
            list = list.filter { it.category == category }
        }

        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter {
                it.name.lowercase().contains(q) ||
                it.callsign.lowercase().contains(q) ||
                it.location.lowercase().contains(q) ||
                it.category.displayName.lowercase().contains(q)
            }
        }

        list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CostaRicaChannelsData.channels)

    fun selectChannel(channel: TvChannel) {
        _selectedChannel.value = channel
    }

    fun nextChannel() {
        val currentList = _allChannels.value
        val currentIndex = currentList.indexOfFirst { it.id == _selectedChannel.value.id }
        if (currentIndex != -1) {
            val nextIndex = (currentIndex + 1) % currentList.size
            _selectedChannel.value = currentList[nextIndex]
        }
    }

    fun previousChannel() {
        val currentList = _allChannels.value
        val currentIndex = currentList.indexOfFirst { it.id == _selectedChannel.value.id }
        if (currentIndex != -1) {
            val prevIndex = if (currentIndex - 1 < 0) currentList.size - 1 else currentIndex - 1
            _selectedChannel.value = currentList[prevIndex]
        }
    }

    fun setCategory(category: ChannelCategory) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedTab(tab: Int) {
        _selectedTab.value = tab
    }

    fun toggleFullscreen(fullscreen: Boolean) {
        _isFullscreen.value = fullscreen
    }

    fun toggleFavorite(channelId: String) {
        favoritesRepository.toggleFavorite(channelId)
    }

    fun isFavorite(channelId: String): Boolean {
        return favoritesRepository.isFavorite(channelId)
    }

    fun toggleReminder(programId: String) {
        favoritesRepository.toggleReminder(programId)
    }

    fun hasReminder(programId: String): Boolean {
        return favoritesRepository.hasReminder(programId)
    }

    /**
     * Manually triggers an immediate synchronization of online EPG from sources.
     */
    fun refreshEpg() {
        viewModelScope.launch {
            onlineEpgRepository.syncEpg(force = true)
        }
    }

    /**
     * Obtains the most up-to-date schedule for a channel (online EPG or tailored fallback).
     */
    fun getScheduleForChannel(channel: TvChannel): List<TvProgram> {
        return onlineEpgRepository.getScheduleForChannel(channel.id, channel.name, channel.category.displayName)
    }

    /**
     * Resolves the current playing program for a channel.
     */
    fun getCurrentProgram(channel: TvChannel): TvProgram {
        val (h, m) = _costaRicaTime.value
        return onlineEpgRepository.getCurrentProgram(channel.id, channel.name, channel.category.displayName, h, m)
    }

    /**
     * Resolves the next upcoming program for a channel.
     */
    fun getNextProgram(channel: TvChannel): TvProgram? {
        val (h, m) = _costaRicaTime.value
        return onlineEpgRepository.getNextProgram(channel.id, channel.name, channel.category.displayName, h, m)
    }
}
