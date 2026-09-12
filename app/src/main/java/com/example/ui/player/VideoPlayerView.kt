package com.example.ui.player

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.data.model.TvChannel
import com.example.ui.theme.LiveRed
import kotlinx.coroutines.delay

enum class ResizeMode(val modeInt: Int, val label: String) {
    FIT(AspectRatioFrameLayout.RESIZE_MODE_FIT, "Ajustar (16:9)"),
    ZOOM(AspectRatioFrameLayout.RESIZE_MODE_ZOOM, "Rellenar pantalla"),
    FILL(AspectRatioFrameLayout.RESIZE_MODE_FILL, "Estirar")
}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerView(
    channel: TvChannel,
    currentProgramTitle: String,
    isFullscreen: Boolean,
    onToggleFullscreen: (Boolean) -> Unit,
    onNextChannel: () -> Unit,
    onPreviousChannel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var isPlaying by remember { mutableStateOf(true) }
    var isMuted by remember { mutableStateOf(false) }
    var isBuffering by remember { mutableStateOf(true) }
    var playbackError by remember { mutableStateOf<String?>(null) }
    var controlsVisible by remember { mutableStateOf(true) }
    var activeStreamIndex by remember(channel.id) { mutableIntStateOf(0) }
    var resizeMode by remember { mutableStateOf(ResizeMode.FIT) }

    // Auto-hide controls after 4 seconds of inactivity
    LaunchedEffect(controlsVisible, isPlaying) {
        if (controlsVisible && isPlaying && playbackError == null) {
            delay(4000)
            controlsVisible = false
        }
    }

    // Initialize ExoPlayer
    val exoPlayer = remember(context) {
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setUserAgent("Mozilla/5.0 (Linux; Android 14; Pixel 8) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36")
            .setConnectTimeoutMs(12000)
            .setReadTimeoutMs(15000)
            .setAllowCrossProtocolRedirects(true)

        val mediaSourceFactory = DefaultMediaSourceFactory(context)
            .setDataSourceFactory(httpDataSourceFactory)

        ExoPlayer.Builder(context)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()
            .apply {
                playWhenReady = true
                repeatMode = Player.REPEAT_MODE_OFF
            }
    }

    // Manage activity fullscreen system bars and orientation
    val activity = context as? Activity
    LaunchedEffect(isFullscreen) {
        activity?.let { act ->
            val window = act.window
            val insetsController = WindowCompat.getInsetsController(window, window.decorView)
            if (isFullscreen) {
                act.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                insetsController.hide(WindowInsetsCompat.Type.systemBars())
                insetsController.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            } else {
                act.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                insetsController.show(WindowInsetsCompat.Type.systemBars())
            }
        }
    }

    // Attach Player Listener
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                isBuffering = playbackState == Player.STATE_BUFFERING
                if (playbackState == Player.STATE_READY) {
                    playbackError = null
                }
            }

            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }

            override fun onPlayerError(error: PlaybackException) {
                isBuffering = false
                val availableStreams = channel.streamUrls
                if (activeStreamIndex + 1 < availableStreams.size) {
                    // Try next fallback stream
                    activeStreamIndex++
                } else {
                    playbackError = "Señal en reconexión (${error.errorCodeName})"
                }
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    // Lifecycle observer to pause when in background
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer.pause()
                }
                Lifecycle.Event.ON_RESUME -> {
                    if (isPlaying) {
                        exoPlayer.play()
                    }
                }
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Update MediaItem when channel or stream index changes
    LaunchedEffect(channel.id, activeStreamIndex) {
        playbackError = null
        isBuffering = true
        val streamUrl = channel.streamUrls.getOrNull(activeStreamIndex) ?: channel.streamUrls.firstOrNull()
        if (streamUrl != null) {
            try {
                val mediaItem = MediaItem.Builder()
                    .setUri(streamUrl)
                    .setMediaId(channel.id)
                    .build()
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.play()
            } catch (e: Exception) {
                playbackError = "Error al iniciar reproducción"
            }
        } else {
            playbackError = "No hay señal disponible"
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                controlsVisible = !controlsVisible
            }
            .testTag("video_player_container")
    ) {
        // ExoPlayer AndroidView
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                PlayerView(ctx).apply {
                    this.player = exoPlayer
                    this.useController = false
                    this.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT)
                    this.layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            update = { playerView ->
                playerView.setResizeMode(resizeMode.modeInt)
            }
        )

        // Buffering Indicator
        if (isBuffering && playbackError == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(44.dp),
                        strokeWidth = 3.dp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Conectando con señal en vivo...",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Playback Error / Reconnect view
        if (playbackError != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.85f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Señal temporalmente no disponible",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Verificando servidores de ${channel.name}...",
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        onClick = {
                            playbackError = null
                            isBuffering = true
                            activeStreamIndex = 0
                            val streamUrl = channel.streamUrls.firstOrNull()
                            if (streamUrl != null) {
                                val item = MediaItem.fromUri(streamUrl)
                                exoPlayer.setMediaItem(item)
                                exoPlayer.prepare()
                                exoPlayer.play()
                            }
                        },
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                Icons.Default.Refresh,
                                contentDescription = "Reintentar señal",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Reintentar señal",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // Overlay Controls
        AnimatedVisibility(
            visible = controlsVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.75f),
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.85f)
                            )
                        )
                    )
            ) {
                // Top bar: Channel Info and Badges
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .align(Alignment.TopCenter),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Live Badge
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = LiveRed,
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .background(Color.White, CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "EN VIVO",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Text(
                                text = channel.name,
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        if (currentProgramTitle.isNotBlank()) {
                            Text(
                                text = currentProgramTitle,
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }

                    // Top Action Icons
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Aspect Ratio cycle
                        IconButton(
                            onClick = {
                                resizeMode = when (resizeMode) {
                                    ResizeMode.FIT -> ResizeMode.ZOOM
                                    ResizeMode.ZOOM -> ResizeMode.FILL
                                    ResizeMode.FILL -> ResizeMode.FIT
                                }
                            },
                            modifier = Modifier.testTag("aspect_ratio_button")
                        ) {
                            Icon(
                                Icons.Default.AspectRatio,
                                contentDescription = "Cambiar relación de aspecto",
                                tint = Color.White
                            )
                        }

                        // Mute / Unmute
                        IconButton(
                            onClick = {
                                isMuted = !isMuted
                                exoPlayer.volume = if (isMuted) 0f else 1f
                            },
                            modifier = Modifier.testTag("mute_button")
                        ) {
                            Icon(
                                if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                                contentDescription = if (isMuted) "Activar sonido" else "Silenciar",
                                tint = Color.White
                            )
                        }
                    }
                }

                // Center Play/Pause and Fast Switch Controls
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Previous Channel
                    IconButton(
                        onClick = onPreviousChannel,
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                            .testTag("prev_channel_button")
                    ) {
                        Icon(
                            Icons.Default.SkipPrevious,
                            contentDescription = "Canal anterior",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    // Play/Pause Main Button
                    IconButton(
                        onClick = {
                            if (isPlaying) {
                                exoPlayer.pause()
                            } else {
                                exoPlayer.play()
                            }
                        },
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color.White.copy(alpha = 0.25f), CircleShape)
                            .testTag("play_pause_button")
                    ) {
                        Icon(
                            if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pausar" else "Reproducir",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    // Next Channel
                    IconButton(
                        onClick = onNextChannel,
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                            .testTag("next_channel_button")
                    ) {
                        Icon(
                            Icons.Default.SkipNext,
                            contentDescription = "Siguiente canal",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                // Bottom bar: Resolution & Fullscreen Toggle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .align(Alignment.BottomCenter),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Quality / location info
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color.White.copy(alpha = 0.2f),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                text = channel.quality,
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Text(
                            text = "${channel.callsign} • ${channel.location}",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    }

                    // Fullscreen Button
                    IconButton(
                        onClick = { onToggleFullscreen(!isFullscreen) },
                        modifier = Modifier.testTag("fullscreen_toggle_button")
                    ) {
                        Icon(
                            if (isFullscreen) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
                            contentDescription = if (isFullscreen) "Salir de pantalla completa" else "Pantalla completa",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    }
}
