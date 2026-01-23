package com.tbc.auth.domain.repository.user_info

import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {
    fun isUserAuthenticated(): Boolean
    fun getAuthState(): Flow<Boolean>
}