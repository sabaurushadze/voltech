package com.tbc.search.data.di

import com.tbc.search.data.repository.SearchItemRepositoryImpl
import com.tbc.search.domain.repository.SearchItemRepository
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
    abstract fun bindSearchItemRepository(impl: SearchItemRepositoryImpl): SearchItemRepository

}