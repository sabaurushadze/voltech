package com.tbc.auth.data.di

import com.tbc.auth.data.repository.login.LogInRepositoryImpl
import com.tbc.auth.data.repository.register.RegisterRepositoryImpl
import com.tbc.auth.data.repository.user_info.UserInfoRepositoryImpl
import com.tbc.auth.domain.repository.login.LogInRepository
import com.tbc.auth.domain.repository.register.RegisterRepository
import com.tbc.auth.domain.repository.user_info.UserInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLogInRepository(impl: LogInRepositoryImpl): LogInRepository

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(impl: RegisterRepositoryImpl): RegisterRepository

    @Binds
    @Singleton
    abstract fun bindUserInfoRepository(impl: UserInfoRepositoryImpl): UserInfoRepository
}