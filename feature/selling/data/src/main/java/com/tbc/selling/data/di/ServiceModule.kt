package com.tbc.selling.data.di

import com.tbc.selling.data.service.SellerService
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
    fun provideSellerService(retrofit: Retrofit): SellerService {
        return retrofit.create(SellerService::class.java)
    }
}