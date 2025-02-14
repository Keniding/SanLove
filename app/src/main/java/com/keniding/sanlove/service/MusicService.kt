package com.keniding.sanlove.service

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MusicService(private val context: Context) {
    private var player: ExoPlayer? = null
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    fun playPreview(previewUrl: String) {
        try {
            if (player == null) {
                player = ExoPlayer.Builder(context).build()
            }

            player?.let { exoPlayer ->
                val mediaItem = MediaItem.fromUri(previewUrl)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true

                exoPlayer.addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        _isPlaying.value = state == Player.STATE_READY && exoPlayer.playWhenReady
                    }

                    override fun onPlayerError(error: PlaybackException) {
                        _isPlaying.value = false
                    }
                })
            }
        } catch (e: Exception) {
            _isPlaying.value = false
        }
    }

    fun togglePlayPause() {
        player?.let { exoPlayer ->
            if (exoPlayer.isPlaying) {
                exoPlayer.pause()
            } else {
                exoPlayer.play()
            }
            _isPlaying.value = exoPlayer.isPlaying
        }
    }

    fun release() {
        player?.let { exoPlayer ->
            exoPlayer.release()
            player = null
            _isPlaying.value = false
        }
    }
}
