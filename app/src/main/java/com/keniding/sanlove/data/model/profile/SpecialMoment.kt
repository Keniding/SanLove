package com.keniding.sanlove.data.model.profile

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class SpecialMoment(
    @get:PropertyName("date") @set:PropertyName("date")
    var date: String = "",

    @get:PropertyName("title") @set:PropertyName("title")
    var title: String = "",

    @get:PropertyName("description") @set:PropertyName("description")
    var description: String = "",

    @get:PropertyName("photoUrl") @set:PropertyName("photoUrl")
    var photoUrl: String = ""
)