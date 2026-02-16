package com.tbc.core.data.di

import com.tbc.core.data.remote.service.recently_viewed.RecentlyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {
    @Provides
    @Singleton
    fun provideSearchService(retrofit: Retrofit): RecentlyService {
        return retrofit.create(RecentlyService::class.java)
    }

}