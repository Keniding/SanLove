package com.keniding.sanlove.data.model.profile

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class RelationshipInfo(
    @get:PropertyName("anniversaryDate") @set:PropertyName("anniversaryDate")
    var anniversaryDate: String = "",

    @get:PropertyName("specialMoments") @set:PropertyName("specialMoments")
    var specialMoments: List<SpecialMoment> = listOf(),

    @get:PropertyName("petNamePartner1") @set:PropertyName("petNamePartner1")
    var petNamePartner1: String = "",

    @get:PropertyName("petNamePartner2") @set:PropertyName("petNamePartner2")
    var petNamePartner2: String = "",

    @get:PropertyName("song") @set:PropertyName("song")
    var song: SongInfo = SongInfo(),

    @get:PropertyName("meetingStory") @set:PropertyName("meetingStory")
    var meetingStory: String = ""
)