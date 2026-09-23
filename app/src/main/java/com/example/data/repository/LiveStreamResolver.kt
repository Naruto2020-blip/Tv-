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
        val streams = mutableListOf(
            "https://geo.dailymotion.com/player/xcdvm.html?video=x7vh8g3",
            "https://www.dailymotion.com/embed/video/x7vh8g3?autoplay=1&mute=0",
            "https://sinartdigital.com/envivo-canaltrece"
        )
        for (url in channel.streamUrls) {
            if (!streams.contains(url)) {
                streams.add(url)
            }
        }
        return streams
    }

    private fun resolveFutvStreams(channel: TvChannel): List<String> {
        val candidates = listOf(
            "http://190.61.90.17:40000/play/a03v/index.m3u8",
            "http://45.186.106.207:8000/play/a03v/index.m3u8"
        )
        val workingStreams = mutableListOf<String>()
        val unreachableStreams = mutableListOf<String>()

        // Fast connectivity probe on current device network (checks if mobile carrier blocks high ports)
        for (url in candidates) {
            var reachable = false
            try {
                val checkReq = Request.Builder()
                    .url(url)
                    .head()
                    .header("User-Agent", "Mozilla/5.0")
                    .build()
                val probeClient = httpClient.newBuilder()
                    .connectTimeout(2500, TimeUnit.MILLISECONDS)
                    .readTimeout(2500, TimeUnit.MILLISECONDS)
                    .build()
                probeClient.newCall(checkReq).execute().use { res ->
                    if (res.isSuccessful || res.code in 200..399) {
                        reachable = true
                    }
                }
            } catch (e: Exception) {
                reachable = false
            }

            if (reachable) {
                workingStreams.add(url)
            } else {
                unreachableStreams.add(url)
            }
        }

        val result = mutableListOf<String>()
        // Prioritize streams confirmed reachable on this device network
        result.addAll(workingStreams)

        // Always add official FUTV match videos & broadcasts (served over Cloudflare HTTPS port 443, never blocked by carriers)
        try {
            val request = Request.Builder()
                .url("https://futvcr.com")
                .header("User-Agent", "Mozilla/5.0 (Linux; Android 14; Mobile) AppleWebKit/537.36")
                .build()
            val response = httpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val html = response.body?.string() ?: ""
                val mp4Matcher = Pattern.compile("https?://futvcr\\.com/wp-content/uploads/[^\"'\\s]+\\.mp4").matcher(html)
                var count = 0
                while (mp4Matcher.find() && count < 5) {
                    val url = mp4Matcher.group()
                    if (!result.contains(url)) {
                        result.add(url)
                        count++
                    }
                }
            }
            response.close()
        } catch (e: Exception) {
            Log.w(TAG, "Error fetching futvcr videos: ${e.message}")
        }

        // Add channel default streams that aren't yet in result
        for (url in channel.streamUrls) {
            if (!result.contains(url) && !unreachableStreams.contains(url)) {
                result.add(url)
            }
        }
        // Finally append any streams that failed probe at the tail end
        for (url in unreachableStreams) {
            if (!result.contains(url)) {
                result.add(url)
            }
        }

        return result
    }
}
