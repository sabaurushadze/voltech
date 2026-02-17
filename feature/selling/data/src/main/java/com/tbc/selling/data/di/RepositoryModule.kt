package com.tbc.selling.data.di

import com.tbc.selling.data.repository.SellerRepositoryImpl
import com.tbc.selling.domain.repository.SellerRepository
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
    abstract fun bindSellerRepository(impl: SellerRepositoryImpl): SellerRepository
}