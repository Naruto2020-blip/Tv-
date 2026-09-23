package com.example.data.repository

import android.util.Log
import com.example.data.model.TvChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/**
 * Dynamic stream resolver that automatically obtains active, tokenized live streams
 * for channels that use tokenized CDNs or dynamic video embeds (e.g. ¡OPA! Canal 38,
 * Trece Costa Rica / SINART, FUTV).
 */
object LiveStreamResolver {

    private const val TAG = "LiveStreamResolver"

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(4, TimeUnit.SECONDS)
        .readTimeout(4, TimeUnit.SECONDS)
        .build()

    // Cache resolved stream with timestamp (cache for 10 minutes)
    private val streamCache = ConcurrentHashMap<String, Pair<Long, List<String>>>()
    private const val CACHE_TTL_MS = 10 * 60 * 1000L

    private val OPA_URL_PATTERN = Pattern.compile("file:\\s*[\"'](https?://[^\"']+\\.m3u8[^\"']*)[\"']")

    suspend fun resolveStreams(channel: TvChannel): List<String> = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        val cached = streamCache[channel.id]
        if (cached != null && (now - cached.first) < CACHE_TTL_MS && cached.second.isNotEmpty()) {
            return@withContext cached.second
        }

        val resolved = when (channel.id) {
            "opacanal38" -> resolveOpaStream(channel)
            "canal13sinart" -> resolveSinartStream(channel)
            "futv" -> resolveFutvStreams(channel)
            else -> channel.streamUrls
        }

        if (resolved.isNotEmpty()) {
            streamCache[channel.id] = Pair(now, resolved)
        }

        resolved.ifEmpty { channel.streamUrls }
    }

    private fun resolveOpaStream(channel: TvChannel): List<String> {
        val streams = mutableListOf<String>()
        try {
            val request = Request.Builder()
                .url("https://conceptoweb-studio.com/radio/video/genteopa/")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .header("Referer", "https://genteopa.com/")
                .build()

            val response = httpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val html = response.body?.string() ?: ""
                val matcher = OPA_URL_PATTERN.matcher(html)
                if (matcher.find()) {
                    val streamUrl = matcher.group(1)
                    if (!streamUrl.isNullOrBlank()) {
                        streams.add(streamUrl)
                    }
                }
            }
            response.close()
        } catch (e: Exception) {
            Log.w(TAG, "Error resolving dynamic OPA stream: ${e.message}")
        }

        // Add channel streamUrls as fallback
        for (url in channel.streamUrls) {
            if (!streams.contains(url)) {
                streams.add(url)
            }
        }
        return streams
    }

    private fun resolveSinartStream(channel: TvChannel): List<String> {
        val streams = mutableListOf<String>()
        try {
            val request = Request.Builder()
                .url("https://www.dailymotion.com/player/metadata/video/x7vh8g3")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .build()

            val response = httpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val jsonStr = response.body?.string() ?: ""
                val root = JSONObject(jsonStr)
                val qualities = root.optJSONObject("qualities")
                val autoArray = qualities?.optJSONArray("auto")
                if (autoArray != null && autoArray.length() > 0) {
                    val streamObj = autoArray.optJSONObject(0)
                    val liveUrl = streamObj?.optString("url")
                    if (!liveUrl.isNullOrBlank()) {
                        streams.add(liveUrl)
                    }
                }
            }
            response.close()
        } catch (e: Exception) {
            Log.w(TAG, "Error resolving SINART Dailymotion stream: ${e.message}")
        }

        for (url in channel.streamUrls) {
            if (!streams.contains(url)) {
                streams.add(url)
            }
        }
        return streams
    }

    private fun resolveFutvStreams(channel: TvChannel): List<String> {
        val streams = mutableListOf<String>()
        // Prioritize verified active ports and reliable fallbacks
        streams.add("http://45.186.106.207:8000/play/a03v/index.m3u8")
        streams.add("http://190.61.90.17:40000/play/a03v/index.m3u8")
        streams.add("https://futvcr.com/wp-content/uploads/2026/09/RESUMEN-FECHA-JORNADA-9-A-26.mp4")
        streams.add("https://cloudvideo.servers10.com:8081/8230/index.m3u8")

        for (url in channel.streamUrls) {
            if (!streams.contains(url)) {
                streams.add(url)
            }
        }
        return streams
    }
}
