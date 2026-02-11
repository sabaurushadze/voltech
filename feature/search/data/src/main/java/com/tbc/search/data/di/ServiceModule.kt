package com.tbc.search.data.di

import com.tbc.search.data.service.cart.CartService
import com.tbc.search.data.service.favorite.FavoriteService
import com.tbc.search.data.service.feed.FeedService
import com.tbc.search.data.service.search.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideSearchService(retrofit: Retrofit): SearchService {
        return retrofit.create(SearchService::class.java)
    }

    @Provides
    @Singleton
    fun provideFeedService(retrofit: Retrofit): FeedService {
        return retrofit.create(FeedService::class.java)
    }

    @Provides
    @Singleton
    fun provideFavoriteService(retrofit: Retrofit): FavoriteService {
        return retrofit.create(FavoriteService::class.java)
    }

    @Provides
    @Singleton
    fun provideCartService(retrofit: Retrofit): CartService {
        return retrofit.create(CartService::class.java)
    }
}