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
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
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

        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        try {
            val date1 = LocalDate.parse(partner1.startDate, dateFormatter)
            val date2 = LocalDate.parse(partner2.startDate, dateFormatter)

            if (date1 != date2) {
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
        } catch (e: DateTimeParseException) {
            throw Exception("Formato de fecha inválido. Use el formato dd/MM/yyyy")
        }
    }

    suspend fun saveProfile(profile: CoupleProfile) {
        profilesRef.child(profile.id).setValue(profile).await()
    }

    suspend fun getProfile(id: String): Result<CoupleProfile> {
        return try {
            val snapshot = profilesRef.child(id).get().await()
            val profile = snapshot.getValue(CoupleProfile::class.java)
            if (profile != null) {
                Log.d("ProfileRepository", "Profile retrieved: $profile")
                Result.success(profile)
            } else {
                Log.d("ProfileRepository", "Profile not found for id: $id")
                Result.failure(Exception("Profile not found"))
            }
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error getting profile", e)
            Result.failure(e)
        }
    }
}
