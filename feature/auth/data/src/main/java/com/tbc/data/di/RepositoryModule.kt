package com.tbc.data.di

import com.tbc.data.repository.login.LogInRepositoryImpl
import com.tbc.data.repository.register.RegisterRepositoryImpl
import com.tbc.data.repository.user_info.UserInfoRepositoryImpl
import com.tbc.domain.repository.login.LogInRepository
import com.tbc.domain.repository.register.RegisterRepository
import com.tbc.domain.repository.user_info.UserInfoRepository
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