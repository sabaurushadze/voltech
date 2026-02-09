package com.tbc.core.data.remote.di

import com.tbc.core.data.remote.repository.user.UserRepositoryImpl
import com.tbc.core.domain.repository.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(impl: UserRepositoryImpl): UserRepository
}