package com.keniding.sanlove.data.model.profile

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class CoupleProfile(
    @get:PropertyName("id") @set:PropertyName("id")
    var id: String = "",

    @get:PropertyName("partner1") @set:PropertyName("partner1")
    var partner1: Partner = Partner(),

    @get:PropertyName("partner2") @set:PropertyName("partner2")
    var partner2: Partner = Partner(),

    @get:PropertyName("relationship") @set:PropertyName("relationship")
    var relationship: RelationshipInfo = RelationshipInfo()
)