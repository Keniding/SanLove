package com.keniding.sanlove.data.model.profile

data class CoupleProfile(
    val id: String,
    val partner1: Partner,
    val partner2: Partner,
    val relationship: RelationshipInfo
)