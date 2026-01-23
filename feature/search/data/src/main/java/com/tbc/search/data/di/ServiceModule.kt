package com.tbc.search.data.di

import com.tbc.search.data.service.SearchItemService
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
    fun provideCategoryService(retrofit: Retrofit): SearchItemService {
        return retrofit.create(SearchItemService::class.java)
    }
}