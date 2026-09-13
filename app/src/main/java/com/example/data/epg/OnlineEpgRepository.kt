package com.example.data.epg

import android.content.Context
import android.util.Log
import com.example.data.model.TvProgram
import com.example.data.repository.CostaRicaEpgData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.InputStream
import java.util.concurrent.TimeUnit
import java.util.zip.GZIPInputStream

data class EpgSyncStatus(
    val isSyncing: Boolean = false,
    val activeSource: String? = null,
    val lastSyncTimeMillis: Long = 0L,
    val channelCountWithOnlineData: Int = 0,
    val totalProgramsLoaded: Int = 0,
    val error: String? = null
)

enum class EpgMode(val displayName: String, val description: String) {
    OFFICIAL("Páginas Oficiales de Canales", "Programación directa de teletica.com, repretel.com, sinartdigital.com, telediario.cr, futvcr.com"),
    ONLINE("Fuentes Online XMLTV", "Sincronización con EPG.lat, EPGShare01, IPTV-org, Open-EPG, TDTChannels")
}

class OnlineEpgRepository(private val context: Context) {

    private val tag = "OnlineEpgRepository"

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .followRedirects(true)
        .build()

    private val cacheFile: File
        get() = File(context.cacheDir, "epg_online_cache.json")

    private val _epgMode = MutableStateFlow(EpgMode.OFFICIAL)
    val epgMode: StateFlow<EpgMode> = _epgMode.asStateFlow()

    fun setEpgMode(mode: EpgMode) {
        _epgMode.value = mode
    }

    fun toggleEpgMode() {
        _epgMode.value = if (_epgMode.value == EpgMode.OFFICIAL) EpgMode.ONLINE else EpgMode.OFFICIAL
    }

    private val _syncStatus = MutableStateFlow(EpgSyncStatus())
    val syncStatus: StateFlow<EpgSyncStatus> = _syncStatus.asStateFlow()

    // Map: localChannelId -> List<TvProgram>
    private val _onlineSchedules = MutableStateFlow<Map<String, List<TvProgram>>>(emptyMap())
    val onlineSchedules: StateFlow<Map<String, List<TvProgram>>> = _onlineSchedules.asStateFlow()

    init {
        // Load local cache synchronously on init for instant offline display
        loadFromDiskCache()
    }

    /**
     * Executes automatic synchronization against public online EPG sources in priority order:
     * 1. EPG.lat Costa Rica
     * 2. EPGShare01
     * 3. IPTV-org / GitHub
     * 4. Open-EPG
     * 5. TDTChannels
     */
    suspend fun syncEpg(force: Boolean = false): Boolean = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        val lastSync = _syncStatus.value.lastSyncTimeMillis

        // Don't re-sync if already fresh (less than 15 minutes) unless forced
        if (!force && lastSync > 0 && (now - lastSync) < 15 * 60 * 1000 && _onlineSchedules.value.isNotEmpty()) {
            return@withContext true
        }

        _syncStatus.value = _syncStatus.value.copy(
            isSyncing = true,
            error = null
        )

        val sources = OnlineEpgSources.allSources
        var success = false
        var lastError: String? = null

        for (source in sources) {
            try {
                Log.d(tag, "Attempting EPG sync from: ${source.name} (${source.url})")
                _syncStatus.value = _syncStatus.value.copy(activeSource = source.name)

                val request = Request.Builder()
                    .url(source.url)
                    .header("User-Agent", "TV-Costa-Rica/1.0 (Android TV EPG Sync)")
                    .build()

                val response = httpClient.newCall(request).execute()
                if (!response.isSuccessful) {
                    val msg = "HTTP ${response.code} from ${source.name}"
                    Log.w(tag, msg)
                    lastError = msg
                    response.close()
                    continue
                }

                val body = response.body
                if (body == null) {
                    response.close()
                    continue
                }

                val rawStream = body.byteStream()
                val parsedStream: InputStream = if (source.isGzip) {
                    GZIPInputStream(rawStream)
                } else {
                    rawStream
                }

                val parsed = XmlTvParser.parse(parsedStream)
                response.close()

                if (parsed.isNotEmpty()) {
                    val totalProgs = parsed.values.sumOf { it.size }
                    Log.d(tag, "Successfully synced ${parsed.size} channels, $totalProgs programs from ${source.name}")

                    _onlineSchedules.value = parsed
                    saveToDiskCache(source.name, parsed)

                    _syncStatus.value = EpgSyncStatus(
                        isSyncing = false,
                        activeSource = source.name,
                        lastSyncTimeMillis = System.currentTimeMillis(),
                        channelCountWithOnlineData = parsed.size,
                        totalProgramsLoaded = totalProgs,
                        error = null
                    )
                    success = true
                    break
                } else {
                    Log.w(tag, "Parsed 0 Costa Rican channels from ${source.name}, trying next source...")
                }
            } catch (e: Exception) {
                Log.e(tag, "Failed to sync from ${source.name}: ${e.message}")
                lastError = e.message ?: "Error al conectar con ${source.name}"
            }
        }

        if (!success) {
            _syncStatus.value = _syncStatus.value.copy(
                isSyncing = false,
                error = lastError ?: "No se pudo actualizar de ninguna fuente online. Usando guía local."
            )
        }

        return@withContext success
    }

    /**
     * Resolves schedule for a channel:
     * - In OFFICIAL mode: Uses 100% verified schedules from channels' official websites (teletica.com, repretel.com, sinartdigital.com, telediario.cr, futvcr.com).
     * - In ONLINE mode: Uses online XMLTV feeds, falling back to official schedules if missing.
     */
    fun getScheduleForChannel(channelId: String, channelName: String, categoryName: String): List<TvProgram> {
        if (_epgMode.value == EpgMode.OFFICIAL) {
            return CostaRicaEpgData.getScheduleForChannel(channelId, channelName, categoryName)
        }
        val onlineList = _onlineSchedules.value[channelId]
        if (!onlineList.isNullOrEmpty() && onlineList.size >= 2) {
            return onlineList
        }
        return CostaRicaEpgData.getScheduleForChannel(channelId, channelName, categoryName)
    }

    /**
     * Resolves the current playing program for a channel.
     */
    fun getCurrentProgram(
        channelId: String,
        channelName: String,
        categoryName: String,
        hour: Int,
        minute: Int
    ): TvProgram {
        val schedule = getScheduleForChannel(channelId, channelName, categoryName)
        val currentMins = hour * 60 + minute

        // 1. Direct match with program currently airing
        val liveMatch = schedule.firstOrNull { it.isLiveAt(hour, minute) }
        if (liveMatch != null) return liveMatch

        // 2. Program that started before current time
        val pastCandidate = schedule.filter {
            val startMins = it.startHour * 60 + it.startMinute
            startMins <= currentMins
        }.maxByOrNull { it.startHour * 60 + it.startMinute }

        return pastCandidate
            ?: schedule.lastOrNull()
            ?: CostaRicaEpgData.getCurrentProgram(channelId, channelName, categoryName, hour, minute)
    }

    /**
     * Resolves the next program for a channel.
     */
    fun getNextProgram(
        channelId: String,
        channelName: String,
        categoryName: String,
        hour: Int,
        minute: Int
    ): TvProgram? {
        val schedule = getScheduleForChannel(channelId, channelName, categoryName)
        val current = getCurrentProgram(channelId, channelName, categoryName, hour, minute)
        val idx = schedule.indexOfFirst { it.id == current.id }
        return if (idx != -1 && idx + 1 < schedule.size) {
            schedule[idx + 1]
        } else {
            schedule.firstOrNull()
        }
    }

    private fun saveToDiskCache(sourceName: String, data: Map<String, List<TvProgram>>) {
        try {
            val root = JSONObject()
            root.put("source", sourceName)
            root.put("timestamp", System.currentTimeMillis())

            val channelsObj = JSONObject()
            data.forEach { (channelId, programs) ->
                val progArray = JSONArray()
                programs.forEach { p ->
                    val pObj = JSONObject().apply {
                        put("id", p.id)
                        put("channelId", p.channelId)
                        put("title", p.title)
                        put("description", p.description)
                        put("startHour", p.startHour)
                        put("startMinute", p.startMinute)
                        put("endHour", p.endHour)
                        put("endMinute", p.endMinute)
                        put("category", p.category)
                    }
                    progArray.put(pObj)
                }
                channelsObj.put(channelId, progArray)
            }
            root.put("channels", channelsObj)

            cacheFile.writeText(root.toString())
        } catch (e: Exception) {
            Log.e(tag, "Failed to write EPG disk cache: ${e.message}")
        }
    }

    private fun loadFromDiskCache() {
        try {
            if (!cacheFile.exists()) return

            val text = cacheFile.readText()
            val root = JSONObject(text)
            val sourceName = root.optString("source", "Caché local")
            val timestamp = root.optLong("timestamp", 0L)
            val channelsObj = root.optJSONObject("channels") ?: return

            val loadedMap = mutableMapOf<String, List<TvProgram>>()
            val keys = channelsObj.keys()
            while (keys.hasNext()) {
                val channelId = keys.next()
                val array = channelsObj.optJSONArray(channelId) ?: continue
                val progs = mutableListOf<TvProgram>()
                for (i in 0 until array.length()) {
                    val pObj = array.optJSONObject(i) ?: continue
                    progs.add(
                        TvProgram(
                            id = pObj.optString("id", "${channelId}_$i"),
                            channelId = pObj.optString("channelId", channelId),
                            title = pObj.optString("title", "Programa"),
                            description = pObj.optString("description", ""),
                            startHour = pObj.optInt("startHour", 0),
                            startMinute = pObj.optInt("startMinute", 0),
                            endHour = pObj.optInt("endHour", 1),
                            endMinute = pObj.optInt("endMinute", 0),
                            category = pObj.optString("category", "General")
                        )
                    )
                }
                if (progs.isNotEmpty()) {
                    loadedMap[channelId] = progs
                }
            }

            if (loadedMap.isNotEmpty()) {
                _onlineSchedules.value = loadedMap
                val totalProgs = loadedMap.values.sumOf { it.size }
                _syncStatus.value = EpgSyncStatus(
                    isSyncing = false,
                    activeSource = sourceName,
                    lastSyncTimeMillis = timestamp,
                    channelCountWithOnlineData = loadedMap.size,
                    totalProgramsLoaded = totalProgs,
                    error = null
                )
            }
        } catch (e: Exception) {
            Log.e(tag, "Failed to load EPG disk cache: ${e.message}")
        }
    }
}
