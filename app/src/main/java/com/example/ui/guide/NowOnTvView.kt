package com.example.ui.guide

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.TvChannel
import com.example.data.repository.CostaRicaEpgData
import com.example.ui.theme.BorderColor
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CrBlue
import com.example.ui.theme.CrGold
import com.example.ui.theme.LiveRed
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.util.Locale

@Composable
fun NowOnTvView(
    channels: List<TvChannel>,
    onTuneInChannel: (TvChannel) -> Unit,
    modifier: Modifier = Modifier
) {
    val (hour, minute) = CostaRicaEpgData.getCurrentCostaRicaTime()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceDark)
    ) {
        // Header
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "AHORA EN LA TELEVISIÓN TICA",
                        color = TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Programas transmitiendo en vivo en este momento",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = CrGold,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format(Locale.US, "%02d:%02d", hour, minute),
                        color = CrGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(channels) { channel ->
                val currentProgram = CostaRicaEpgData.getCurrentProgram(
                    channel.id,
                    channel.name,
                    channel.category.displayName
                )
                val nextProgram = CostaRicaEpgData.getNextProgram(
                    channel.id,
                    channel.name,
                    channel.category.displayName
                )
                val progress = currentProgram.getProgress(hour, minute)
                val remaining = currentProgram.remainingMinutes(hour, minute)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, BorderColor.copy(alpha = 0.6f), RoundedCornerShape(14.dp))
                        .clickable { onTuneInChannel(channel) }
                        .testTag("now_card_${channel.id}"),
                    colors = CardDefaults.cardColors(containerColor = CardBackground)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Channel Logo
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF0F172A))
                                    .padding(3.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (channel.logoUrl.isNotBlank()) {
                                    AsyncImage(
                                        model = channel.logoUrl,
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                } else {
                                    Icon(
                                        Icons.Outlined.Tv,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            // Channel name and live tag
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = LiveRed,
                                        modifier = Modifier.padding(end = 6.dp)
                                    ) {
                                        Text(
                                            text = "EN VIVO",
                                            color = Color.White,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                        )
                                    }

                                    Text(
                                        text = channel.name,
                                        color = TextPrimary,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = currentProgram.title,
                                    color = Color(0xFF60A5FA),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            // Sintonizar Play Button
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(CrBlue, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.PlayArrow,
                                    contentDescription = "Sintonizar ${channel.name}",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Progress and time remaining
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${currentProgram.timeSpanFormatted} • ${currentProgram.category}",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                            Text(
                                text = if (remaining > 0) "Quedan $remaining min" else "Finalizando",
                                color = CrGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(3.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            color = CrBlue,
                            trackColor = Color(0xFF334155)
                        )

                        // Next Program teaser
                        if (nextProgram != null) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "A continuación: ${nextProgram.startTimeFormatted} - ${nextProgram.title}",
                                color = TextSecondary.copy(alpha = 0.8f),
                                fontSize = 10.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}
