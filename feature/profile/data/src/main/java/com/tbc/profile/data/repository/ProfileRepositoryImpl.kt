package com.tbc.profile.data.repository

import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.profile.domain.repository.ProfileRepository
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class ProfileRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : ProfileRepository {

    override suspend fun updateProfilePhoto(
        photoUrl: String?,
    ): Resource<Unit, DataError.Firestore> {

        val user = firebaseAuth.currentUser
            ?: return Resource.Failure(DataError.Firestore.Unauthenticated)

        val request = UserProfileChangeRequest.Builder()
            .setPhotoUri(photoUrl?.toUri())
            .build()

        return try {
            user.updateProfile(request).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(DataError.Firestore.Unknown)
        }
    }

    override suspend fun updateUsername(username: String): Resource<Unit, DataError.Firestore> {
        val user = firebaseAuth.currentUser
            ?: return Resource.Failure(DataError.Firestore.Unauthenticated)

        val request = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()

        return try {
            user.updateProfile(request).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(DataError.Firestore.Unknown)
        }
    }
}