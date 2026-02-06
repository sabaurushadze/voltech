package com.tbc.profile.domain.repository

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource

interface ProfileRepository {
    suspend fun updateProfilePhoto(photoUrl: String): Resource<Unit, DataError.Firestore>
}