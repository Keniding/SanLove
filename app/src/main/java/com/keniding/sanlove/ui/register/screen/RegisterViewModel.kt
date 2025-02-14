package com.keniding.sanlove.ui.register.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keniding.sanlove.data.model.profile.CoupleProfile
import com.keniding.sanlove.data.model.profile.Partner
import com.keniding.sanlove.data.model.profile.RelationshipInfo
import com.keniding.sanlove.data.model.profile.SongInfo
import com.keniding.sanlove.data.model.register.RegistrationState
import com.keniding.sanlove.data.repository.ProfileRepository
import com.keniding.sanlove.domain.enums.RegistrationStep
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class RegisterViewModel(
    private val repository: ProfileRepository
) : ViewModel() {
    private val _registrationState = MutableStateFlow(RegistrationState())
    val registrationState = _registrationState.asStateFlow()

    fun startRegistration(name: String, birthDate: String) {
        viewModelScope.launch {
            try {
                val temporaryCode = generateTemporaryCode()
                val partner = Partner(
                    name = name,
                    birthDate = birthDate,
                    avatar = "",  // Se puede añadir después
                    nickname = "" // Se puede añadir después
                )

                repository.createTemporaryProfile(temporaryCode, partner)
                _registrationState.update {
                    it.copy(
                        registrationStep = RegistrationStep.WAITING_PARTNER,
                        temporaryCode = temporaryCode,
                        partner1 = partner
                    )
                }
            } catch (e: Exception) {
                _registrationState.update { it.copy(error = e.message) }
            }
        }
    }

    private fun generateTemporaryCode(): String {
        return UUID.randomUUID().toString().take(6).uppercase()
    }

    fun getPartner1Name(): String {
        return _registrationState.value.partner1?.name ?: ""
    }

    fun getPartner2Name(): String {
        return _registrationState.value.partner2?.name ?: ""
    }

    fun completeProfile(
        petName1: String,
        petName2: String,
        songTitle: String,
        songArtist: String,
        meetingStory: String,
        anniversaryDate: String
    ) {
        viewModelScope.launch {
            try {
                val currentState = _registrationState.value
                val profileId = currentState.profileId ?: UUID.randomUUID().toString()
                val updatedProfile = CoupleProfile(
                    id = profileId,
                    partner1 = currentState.partner1!!,
                    partner2 = currentState.partner2!!,
                    relationship = RelationshipInfo(
                        anniversaryDate = anniversaryDate,
                        specialMoments = listOf(),
                        petNamePartner1 = petName1,
                        petNamePartner2 = petName2,
                        song = SongInfo(
                            title = songTitle,
                            artist = songArtist,
                            spotifyId = "",
                            previewUrl = null
                        ),
                        meetingStory = meetingStory
                    )
                )
                repository.saveProfile(updatedProfile)

                _registrationState.update {
                    it.copy(
                        registrationStep = RegistrationStep.COMPLETED,
                        profileId = profileId
                    )
                }
            } catch (e: Exception) {
                _registrationState.update { it.copy(error = e.message) }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun connectWithPartner(
        code: String,
        name: String,
        birthDate: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val partner2 = Partner(
                    name = name,
                    birthDate = birthDate,
                    avatar = "",
                    nickname = ""
                )

                val profileId = repository.connectPartners(code, partner2)

                _registrationState.update {
                    it.copy(
                        registrationStep = RegistrationStep.COMPLETING_PROFILE,
                        profileId = profileId,
                        partner2 = partner2
                    )
                }

                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Error al conectar con tu pareja")
            }
        }
    }
}
