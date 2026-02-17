package com.tbc.profile.data.di

import com.tbc.profile.data.repository.edit_profile.ProfileRepositoryImpl
import com.tbc.profile.domain.repository.edit_profile.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository
}