package com.tbc.search.data.di

import com.tbc.search.data.repository.cart.CartRepositoryImpl
import com.tbc.search.data.repository.favorite.FavoriteRepositoryImpl
import com.tbc.search.data.repository.feed.FeedRepositoryImpl
import com.tbc.search.data.repository.review.ReviewRepositoryImpl
import com.tbc.search.data.repository.search.SearchItemRepositoryImpl
import com.tbc.search.domain.repository.cart.CartRepository
import com.tbc.search.domain.repository.favorite.FavoriteRepository
import com.tbc.search.domain.repository.feed.FeedRepository
import com.tbc.search.domain.repository.review.ReviewRepository
import com.tbc.search.domain.repository.search.SearchItemRepository
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
    abstract fun bindSearchItemRepository(impl: SearchItemRepositoryImpl): SearchItemRepository

    @Binds
    @Singleton
    abstract fun bindFeedRepository(impl: FeedRepositoryImpl): FeedRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(impl: CartRepositoryImpl): CartRepository

    @Binds
    @Singleton
    abstract fun bindReviewRepository(impl: ReviewRepositoryImpl): ReviewRepository
}