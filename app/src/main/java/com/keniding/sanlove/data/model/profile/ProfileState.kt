package com.keniding.sanlove.data.model.profile;

data class ProfileState(
        val profile: CoupleProfile? = null,
        val isLoading: Boolean = false,
        val error: String? = null
)