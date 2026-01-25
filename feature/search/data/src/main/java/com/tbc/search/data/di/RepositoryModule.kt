package com.tbc.search.data.di

import com.tbc.search.data.repository.feed.FeedRepositoryImpl
import com.tbc.search.data.repository.search.SearchItemRepositoryImpl
import com.tbc.search.domain.repository.feed.FeedRepository
import com.tbc.search.domain.repository.search.SearchItemRepository
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

    @Binds
    @Singleton
    abstract fun bindFeedRepository(impl: FeedRepositoryImpl): FeedRepository

}