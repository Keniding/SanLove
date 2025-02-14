package com.keniding.sanlove.data.model

import com.google.firebase.auth.FirebaseUser

data class AuthState(
    val isAuthenticated: Boolean = false,
    val isProfileComplete: Boolean = false,
    val user: FirebaseUser? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
