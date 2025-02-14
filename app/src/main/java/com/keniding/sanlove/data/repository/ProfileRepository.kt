package com.keniding.sanlove.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.database.FirebaseDatabase
import com.keniding.sanlove.data.model.profile.CoupleProfile
import com.keniding.sanlove.data.model.profile.Partner
import com.keniding.sanlove.data.model.profile.RelationshipInfo
import com.keniding.sanlove.data.model.profile.SongInfo
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.util.UUID

class ProfileRepository {
    private val database = FirebaseDatabase.getInstance()
    private val profilesRef = database.getReference("profiles")
    private val temporaryProfilesRef = database.getReference("temporary_profiles")

    suspend fun createTemporaryProfile(code: String, partner: Partner) {
        temporaryProfilesRef.child(code).setValue(partner).await()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun connectPartners(code: String, partner2: Partner): String {
        val snapshot = temporaryProfilesRef.child(code).get().await()
        val partner1 = snapshot.getValue(Partner::class.java)
            ?: throw Exception("Código de conexión inválido")

        if (partner1.startDate != partner2.startDate) {
            throw Exception("Las fechas de inicio de la relación no coinciden. Por favor, verifiquen juntos la fecha correcta.")
        }

        val profileId = UUID.randomUUID().toString()
        val profile = CoupleProfile(
            id = profileId,
            partner1 = partner1,
            partner2 = partner2,
            relationship = RelationshipInfo(
                anniversaryDate = partner1.startDate,
                specialMoments = listOf(),
                petNamePartner1 = "",
                petNamePartner2 = "",
                song = SongInfo(),
                meetingStory = ""
            )
        )

        saveProfile(profile)
        temporaryProfilesRef.child(code).removeValue().await()
        return profileId
    }

    suspend fun saveProfile(profile: CoupleProfile) {
        profilesRef.child(profile.id).setValue(profile).await()
    }

    suspend fun getProfile(id: String): CoupleProfile {
        return try {
            val snapshot = profilesRef.child(id).get().await()
            snapshot.getValue(CoupleProfile::class.java)
                ?.also { Log.d("ProfileRepository", "Profile retrieved: $it") }
                ?: throw Exception("Profile not found")
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error getting profile", e)
            throw e
        }
    }
}
