package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LiveTv
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.CostaRicaEpgData
import com.example.ui.TvViewModel
import com.example.ui.components.CategoryChips
import com.example.ui.components.ChannelCard
import com.example.ui.components.TvSearchBar
import com.example.ui.guide.EpgTimelineView
import com.example.ui.guide.NowOnTvView
import com.example.ui.player.VideoPlayerView
import com.example.ui.theme.BorderColor
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CrBlue
import com.example.ui.theme.CrGold
import com.example.ui.theme.CrRed
import com.example.ui.theme.LiveRed
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun MainTvScreen(
    viewModel: TvViewModel,
    modifier: Modifier = Modifier
) {
    val selectedChannel by viewModel.selectedChannel.collectAsState()
    val allChannels by viewModel.allChannels.collectAsState()
    val displayedChannels by viewModel.displayedChannels.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val isFullscreen by viewModel.isFullscreen.collectAsState()
    val favoriteIds by viewModel.favoriteChannelIds.collectAsState()
    val reminderIds by viewModel.reminderProgramIds.collectAsState()

    // Current program title for selected channel
    val currentProg = CostaRicaEpgData.getCurrentProgram(
        selectedChannel.id,
        selectedChannel.name,
        selectedChannel.category.displayName
    )

    // Handle back button when in fullscreen
    BackHandler(enabled = isFullscreen) {
        viewModel.toggleFullscreen(false)
    }

    if (isFullscreen) {
        // Fullscreen Mode: Video player takes 100% of display
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            VideoPlayerView(
                channel = selectedChannel,
                currentProgramTitle = currentProg.title,
                isFullscreen = true,
                onToggleFullscreen = { viewModel.toggleFullscreen(it) },
                onNextChannel = { viewModel.nextChannel() },
                onPreviousChannel = { viewModel.previousChannel() }
            )
        }
    } else {
        // Portrait / Standard Layout
        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .background(SurfaceDark),
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = TextPrimary,
                    tonalElevation = 6.dp,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .testTag("main_bottom_nav")
                ) {
                    val items = listOf(
                        Triple(0, "Canales", Icons.Filled.Tv to Icons.Outlined.Tv),
                        Triple(1, "Guía EPG", Icons.Filled.CalendarMonth to Icons.Outlined.CalendarMonth),
                        Triple(2, "En Vivo", Icons.Filled.LiveTv to Icons.Outlined.LiveTv),
                        Triple(3, "Favoritos", Icons.Filled.Star to Icons.Outlined.StarBorder)
                    )

                    items.forEach { (index, title, icons) ->
                        val isSelected = selectedTab == index
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.setSelectedTab(index) },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) icons.first else icons.second,
                                    contentDescription = title,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = title,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = Color.White,
                                indicatorColor = CrBlue,
                                unselectedIconColor = TextMuted,
                                unselectedTextColor = TextMuted
                            ),
                            modifier = Modifier.testTag("nav_tab_$index")
                        )
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(SurfaceDark)
            ) {
                // Top Header with Branding
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = CrBlue,
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(7.dp)
                                            .background(CrRed, RoundedCornerShape(2.dp))
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "CR",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }

                            Text(
                                text = "TV Costa Rica",
                                color = TextPrimary,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }

                        // Live status indicator
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF1E293B)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(LiveRed, RoundedCornerShape(3.dp))
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "DIRECTO",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                // In-App Video Player (16:9 Aspect Ratio)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .background(Color.Black)
                ) {
                    VideoPlayerView(
                        channel = selectedChannel,
                        currentProgramTitle = currentProg.title,
                        isFullscreen = false,
                        onToggleFullscreen = { viewModel.toggleFullscreen(it) },
                        onNextChannel = { viewModel.nextChannel() },
                        onPreviousChannel = { viewModel.previousChannel() },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Body content based on selected tab
                when (selectedTab) {
                    0 -> {
                        // "Canales" Tab
                        Column(modifier = Modifier.fillMaxSize()) {
                            TvSearchBar(
                                query = searchQuery,
                                onQueryChange = { viewModel.setSearchQuery(it) }
                            )

                            CategoryChips(
                                selectedCategory = selectedCategory,
                                onSelectCategory = { viewModel.setCategory(it) }
                            )

                            // Channel Count and Info
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${displayedChannels.size} Canales disponibles",
                                    color = TextSecondary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Reproducción continua",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }

                            if (displayedChannels.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(32.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            Icons.Outlined.SearchOff,
                                            contentDescription = null,
                                            tint = TextMuted,
                                            modifier = Modifier.size(48.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "No se encontraron canales",
                                            color = TextSecondary,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = "Prueba con otra palabra o categoría",
                                            color = TextMuted,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            } else {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(displayedChannels, key = { it.id }) { channel ->
                                        val isSelected = channel.id == selectedChannel.id
                                        val isFav = favoriteIds.contains(channel.id)
                                        val chProgram = CostaRicaEpgData.getCurrentProgram(
                                            channel.id,
                                            channel.name,
                                            channel.category.displayName
                                        )

                                        ChannelCard(
                                            channel = channel,
                                            currentProgram = chProgram,
                                            isSelected = isSelected,
                                            isFavorite = isFav,
                                            onSelectChannel = { viewModel.selectChannel(channel) },
                                            onToggleFavorite = { viewModel.toggleFavorite(channel.id) }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    1 -> {
                        // "Guía TV (EPG)" Tab
                        EpgTimelineView(
                            channels = allChannels,
                            selectedChannel = selectedChannel,
                            onSelectChannel = { viewModel.selectChannel(it) },
                            hasReminder = { viewModel.hasReminder(it) },
                            onToggleReminder = { viewModel.toggleReminder(it) },
                            onTuneInChannel = {
                                viewModel.selectChannel(it)
                            }
                        )
                    }

                    2 -> {
                        // "En Vivo Ahora" Tab
                        NowOnTvView(
                            channels = allChannels,
                            onTuneInChannel = {
                                viewModel.selectChannel(it)
                            }
                        )
                    }

                    3 -> {
                        // "Favoritos" Tab
                        val favChannels = allChannels.filter { favoriteIds.contains(it.id) }

                        Column(modifier = Modifier.fillMaxSize()) {
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Star,
                                        contentDescription = null,
                                        tint = CrGold,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "TUS CANALES FAVORITOS (${favChannels.size})",
                                        color = TextPrimary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp
                                    )
                                }
                            }

                            if (favChannels.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(32.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            Icons.Outlined.StarBorder,
                                            contentDescription = null,
                                            tint = TextMuted,
                                            modifier = Modifier.size(56.dp)
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Text(
                                            text = "Aún no tienes canales favoritos",
                                            color = TextPrimary,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Toca la estrella en cualquier canal para agregarlo a esta lista de acceso rápido.",
                                            color = TextSecondary,
                                            fontSize = 12.sp,
                                            modifier = Modifier.padding(horizontal = 24.dp)
                                        )
                                    }
                                }
                            } else {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(favChannels, key = { it.id }) { channel ->
                                        val isSelected = channel.id == selectedChannel.id
                                        val chProgram = CostaRicaEpgData.getCurrentProgram(
                                            channel.id,
                                            channel.name,
                                            channel.category.displayName
                                        )

                                        ChannelCard(
                                            channel = channel,
                                            currentProgram = chProgram,
                                            isSelected = isSelected,
                                            isFavorite = true,
                                            onSelectChannel = { viewModel.selectChannel(channel) },
                                            onToggleFavorite = { viewModel.toggleFavorite(channel.id) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
