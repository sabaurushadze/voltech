package com.tbc.core.domain.repository.user

import com.tbc.core.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUser(): User?
    fun signOut()
}