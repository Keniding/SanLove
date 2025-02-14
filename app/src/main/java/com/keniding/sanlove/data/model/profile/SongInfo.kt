package com.keniding.sanlove.data.model.profile

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class SongInfo(
    @get:PropertyName("title") @set:PropertyName("title")
    var title: String = "",

    @get:PropertyName("artist") @set:PropertyName("artist")
    var artist: String = "",

    @get:PropertyName("spotifyId") @set:PropertyName("spotifyId")
    var spotifyId: String = "",

    @get:PropertyName("previewUrl") @set:PropertyName("previewUrl")
    var previewUrl: String? = null
)
