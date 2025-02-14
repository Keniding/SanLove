package com.keniding.sanlove.data.model.profile

data class SongInfo(
    val title: String,
    val artist: String,
    val spotifyId: String,
    val previewUrl: String? = null
)