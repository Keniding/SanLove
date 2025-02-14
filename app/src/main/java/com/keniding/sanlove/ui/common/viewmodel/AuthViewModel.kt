package com.keniding.sanlove.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.keniding.sanlove.data.model.AuthState
import com.keniding.sanlove.data.model.profile.CoupleProfile
import com.keniding.sanlove.data.model.profile.Partner
import com.keniding.sanlove.data.repository.AuthRepository
import com.keniding.sanlove.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.getAuthStateFlow().collect { user ->
                _authState.update {
                    it.copy(
                        isAuthenticated = user != null,
                        user = user
                    )
                }
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                _authState.update { it.copy(isLoading = true) }
                val result = authRepository.signInWithGoogle(idToken)
                handleSignInResult(result.user)
            } catch (e: Exception) {
                _authState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    private suspend fun handleSignInResult(user: FirebaseUser?) {
        user?.let {
            try {
                val profileResult = profileRepository.getProfile(it.uid)

                if (profileResult.isSuccess) {
                    val profile = profileResult.getOrNull()!!
                    _authState.update { state ->
                        state.copy(
                            isAuthenticated = true,
                            isProfileComplete = isProfileComplete(profile),
                            user = user,
                            isLoading = false
                        )
                    }
                } else {
                    val newProfile = CoupleProfile(
                        id = it.uid,
                        partner1 = Partner(
                            name = it.displayName ?: "",
                            avatar = it.photoUrl?.toString() ?: ""
                        )
                    )
                    profileRepository.saveProfile(newProfile)
                    _authState.update { state ->
                        state.copy(
                            isAuthenticated = true,
                            isProfileComplete = false,
                            user = user,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _authState.update { state ->
                    state.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    private fun isProfileComplete(profile: CoupleProfile): Boolean {
        return profile.partner2.name.isNotEmpty() &&
                profile.partner1.startDate.isNotEmpty()
    }


    fun signOut() {
        viewModelScope.launch {
            try {
                authRepository.signOut()
                _authState.update {
                    AuthState()
                }
            } catch (e: Exception) {
                _authState.update {
                    it.copy(error = e.message)
                }
            }
        }
    }
}
