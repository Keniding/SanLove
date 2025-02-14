package com.keniding.sanlove.data.repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.keniding.sanlove.data.model.profile.CoupleProfile
import com.keniding.sanlove.data.model.profile.SpecialMoment
import kotlinx.coroutines.tasks.await

class ProfileRepository {
    private val database = FirebaseDatabase.getInstance()
    private val profilesRef = database.getReference("profiles")

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

    suspend fun saveProfile(profile: CoupleProfile) {
        profilesRef.child(profile.id).setValue(profile).await()
    }

    suspend fun addSpecialMoment(profileId: String, moment: SpecialMoment) {
        val profile = getProfile(profileId)
        val updatedMoments = profile.relationship.specialMoments.toMutableList()
        updatedMoments.add(moment)

        profilesRef.child(profileId)
            .child("relationship")
            .child("specialMoments")
            .setValue(updatedMoments)
            .await()
    }
}
