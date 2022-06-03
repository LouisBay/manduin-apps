package com.bangkit.manduin.di

import android.content.Context
import com.bangkit.manduin.data.remote.retrofit.ManduinApiService
import com.bangkit.manduin.data.remote.retrofit.NewsApiService
import com.bangkit.manduin.data.repository.ApiDataRepository
import com.bangkit.manduin.data.repository.AuthRepository
import com.bangkit.manduin.data.repository.MLRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = AuthRepository()

    @Provides
    @Singleton
    fun provideApiDataRepository(
        newsApiService: NewsApiService,
        manduinApiService: ManduinApiService
    ): ApiDataRepository = ApiDataRepository(newsApiService, manduinApiService)

    @Provides
    @Singleton
    fun provideMLRepository(@ApplicationContext context: Context): MLRepository = MLRepository(context)
}