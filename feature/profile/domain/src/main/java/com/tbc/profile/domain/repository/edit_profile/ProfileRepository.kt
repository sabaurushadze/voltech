package com.tbc.profile.domain.repository.edit_profile

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource

interface ProfileRepository {
    suspend fun updateProfilePhoto(photoUrl: String?): Resource<Unit, DataError.Firestore>
    suspend fun updateUsername(username: String) : Resource<Unit, DataError.Firestore>
}