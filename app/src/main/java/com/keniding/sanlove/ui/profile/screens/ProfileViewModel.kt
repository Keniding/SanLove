package com.keniding.sanlove.ui.profile.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keniding.sanlove.data.model.profile.CoupleProfile
import com.keniding.sanlove.data.model.profile.Partner
import com.keniding.sanlove.data.model.profile.ProfileState
import com.keniding.sanlove.data.model.profile.RelationshipInfo
import com.keniding.sanlove.data.model.profile.SongInfo
import com.keniding.sanlove.data.model.profile.SpecialMoment
import com.keniding.sanlove.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState = _uiState.asStateFlow()

    init {
        createInitialProfile()
    }

    private fun createInitialProfile() {
        viewModelScope.launch {
            try {
                val initialProfile = CoupleProfile(
                    id = "1",
                    partner1 = Partner(
                        name = "Juan",
                        nickname = "Mi Rey",
                        avatar = "https://i.pinimg.com/474x/39/8f/0b/398f0b34e7b34959b5b47831c0e4d6ae.jpg",
                        birthDate = "15/03/1995"
                    ),
                    partner2 = Partner(
                        name = "María",
                        nickname = "Mi Reina",
                        avatar = "https://i.pinimg.com/474x/73/33/1f/73331ff5c5628331cf95a2214d8f8eec.jpg",
                        birthDate = "22/07/1996"
                    ),
                    relationship = RelationshipInfo(
                        anniversaryDate = "14/02/2023",
                        specialMoments = listOf(
                            SpecialMoment(
                                date = "14/02/2023",
                                title = "Nuestro Primer Beso",
                                description = "Fue un momento mágico bajo las estrellas...",
                                photoUrl = "https://i.pinimg.com/736x/d0/99/e6/d099e6887e330d7605a9eefce9799b4b.jpg"
                            )
                        ),
                        petNamePartner1 = "Osito",
                        petNamePartner2 = "Princesa",
                        song = SongInfo(
                            title = "Perfect",
                            artist = "Ed Sheeran",
                            spotifyId = "0tgVpDi06FyKpA1z0VMD4v",
                            previewUrl = "https://p.scdn.co/mp3-preview/9779493d90a47f29e4257aa45bc6146d1ee9cb26"
                        ),
                        meetingStory = "Nos conocimos en una cafetería un día lluvioso..."
                    )
                )

                repository.saveProfile(initialProfile)
                Log.d("ProfileViewModel", "Perfil guardado exitosamente")
                loadProfile()
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error al guardar perfil", e)
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            try {
                val profile = repository.getProfile("1")
                _uiState.update { it.copy(profile = profile) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}
