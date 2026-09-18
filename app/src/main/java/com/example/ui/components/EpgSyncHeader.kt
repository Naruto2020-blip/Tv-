package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.epg.EpgMode
import com.example.data.epg.EpgSyncStatus
import com.example.ui.theme.CrGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun EpgSyncHeader(
    syncStatus: EpgSyncStatus,
    onRefresh: (() -> Unit)? = null,
    epgMode: EpgMode = EpgMode.OFFICIAL,
    onToggleMode: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("epg_sync_header")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    if (epgMode == EpgMode.OFFICIAL) {
                        // Official channels programming badge
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF00C853))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Parrilla Oficial de Canales de Costa Rica",
                                    color = TextPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verificado",
                                    tint = Color(0xFF00C853),
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                            Text(
                                text = "Páginas oficiales: teletica.com · repretel.com · sinartdigital.com · telediario.cr · futvcr.com",
                                color = TextMuted,
                                fontSize = 9.5.sp,
                                maxLines = 1
                            )
                        }
                    } else {
                        // Online XMLTV mode
                        if (syncStatus.isSyncing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Sincronizando fuentes XMLTV online...",
                                    color = TextPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Consultando ${syncStatus.activeSource ?: "IPTV-org, EPGShare, EPG.lat..."}",
                                    color = TextMuted,
                                    fontSize = 9.5.sp,
                                    maxLines = 1
                                )
                            }
                        } else {
                            val isOnline = syncStatus.lastSyncTimeMillis > 0L
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(if (isOnline) Color(0xFF29B6F6) else CrGold)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = if (isOnline) "Fuentes XMLTV Online Sincronizadas" else "Fuentes Online",
                                        color = TextPrimary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (isOnline) {
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = Icons.Default.CloudDone,
                                            contentDescription = null,
                                            tint = Color(0xFF29B6F6),
                                            modifier = Modifier.size(13.dp)
                                        )
                                    }
                                }
                                val lastUpdatedStr = if (syncStatus.lastSyncTimeMillis > 0L) {
                                    val sdf = SimpleDateFormat("hh:mm a", Locale.US).apply {
                                        timeZone = TimeZone.getTimeZone("America/Costa_Rica")
                                    }
                                    val timeStr = sdf.format(Date(syncStatus.lastSyncTimeMillis))
                                    val src = syncStatus.activeSource ?: "EPG.lat"
                                    "Fuente: $src • $timeStr (${syncStatus.totalProgramsLoaded} programas)"
                                } else {
                                    "Fuentes: GatoTV, AmericaTVGuide, EPG.lat, EPGShare, TDTChannels"
                                }
                                Text(
                                    text = lastUpdatedStr,
                                    color = TextMuted,
                                    fontSize = 9.5.sp,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }

                // Action buttons: toggle mode (actualización automática activa)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (onToggleMode != null) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    if (epgMode == EpgMode.OFFICIAL) Color(0xFF00C853).copy(alpha = 0.2f)
                                    else Color(0xFF29B6F6).copy(alpha = 0.2f)
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (epgMode == EpgMode.OFFICIAL) Color(0xFF00C853).copy(alpha = 0.5f)
                                    else Color(0xFF29B6F6).copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .clickable { onToggleMode() }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (epgMode == EpgMode.OFFICIAL) "Modo Oficial" else "Modo Online",
                                color = if (epgMode == EpgMode.OFFICIAL) Color(0xFF00E676) else Color(0xFF81D4FA),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
