package com.keniding.sanlove.domain.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.keniding.sanlove.service.MusicService

@Composable
fun rememberMusicService(): MusicService {
    val context = LocalContext.current
    return remember(context) {
        MusicService(context)
    }
}
