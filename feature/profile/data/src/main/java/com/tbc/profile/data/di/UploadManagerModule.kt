package com.tbc.profile.data.di

import com.tbc.profile.data.manager.UploadManager
import com.tbc.profile.domain.repository.FileUploadManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UploadManagerModule {

    @Binds
    abstract fun bindFileUploadManager(uploadManager: UploadManager): FileUploadManager
}