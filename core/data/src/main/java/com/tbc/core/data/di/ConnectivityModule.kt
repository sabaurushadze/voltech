package com.tbc.core.data.di

import com.tbc.core.data.remote.network.ConnectivityObserverImpl
import com.tbc.core.domain.network.ConnectivityObserver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectivityModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityManager(
        impl: ConnectivityObserverImpl,
    ): ConnectivityObserver

}