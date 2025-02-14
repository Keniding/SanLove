package com.keniding.sanlove.data.model.profile

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class Partner(
    @get:PropertyName("name") @set:PropertyName("name")
    var name: String = "",

    @get:PropertyName("nickname") @set:PropertyName("nickname")
    var nickname: String = "",

    @get:PropertyName("avatar") @set:PropertyName("avatar")
    var avatar: String = "",

    @get:PropertyName("birthDate") @set:PropertyName("birthDate")
    var birthDate: String = ""
)