package com.tbc.core.data.di

import com.tbc.core.data.remote.repository.recently_viewed.RecentlyRepositoryImpl
import com.tbc.core.domain.repository.recently_viewed.RecentlyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRecentlyRepository(impl: RecentlyRepositoryImpl): RecentlyRepository
}