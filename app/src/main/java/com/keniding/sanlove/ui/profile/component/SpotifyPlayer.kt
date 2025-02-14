package com.keniding.sanlove.ui.profile.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keniding.sanlove.data.model.profile.SongInfo
import com.keniding.sanlove.domain.utils.rememberMusicService
import com.keniding.sanlove.ui.common.theme.ValentineColors
import com.keniding.sanlove.ui.valentine.component.glass.GlassCard
import kotlinx.coroutines.launch

@Composable
fun SpotifyPlayer(song: SongInfo) {
    val musicService = rememberMusicService()
    val scope = rememberCoroutineScope()
    val isPlaying by musicService.isPlaying.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            musicService.release()
        }
    }

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = song.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = song.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (song.previewUrl != null) {
                IconButton(
                    onClick = {
                        scope.launch {
                            if (!isPlaying) {
                                musicService.playPreview(song.previewUrl)
                            } else {
                                musicService.togglePlayPause()
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Close else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pausar" else "Reproducir",
                        tint = ValentineColors.DeepRose
                    )
                }
            }
        }
    }
}
