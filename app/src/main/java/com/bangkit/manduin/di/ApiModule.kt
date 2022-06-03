package com.bangkit.manduin.di

import com.bangkit.manduin.data.remote.retrofit.ApiConfig
import com.bangkit.manduin.data.remote.retrofit.ManduinApiService
import com.bangkit.manduin.data.remote.retrofit.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideNewsApiService(): NewsApiService = ApiConfig.getNewsApiService()

    @Provides
    @Singleton
    fun provideManduinApiService(): ManduinApiService = ApiConfig.getManduinApiService()
}