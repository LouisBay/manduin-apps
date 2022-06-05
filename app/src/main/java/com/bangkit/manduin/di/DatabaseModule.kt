package com.bangkit.manduin.di

import android.content.Context
import androidx.room.Room
import com.bangkit.manduin.data.local.room.ManduinDatabase
import com.bangkit.manduin.data.local.room.PlaceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun providePlaceDao(manduinDatabase: ManduinDatabase): PlaceDao = manduinDatabase.placeDao()

    @Provides
    @Singleton
    fun provideManduinDatabase(@ApplicationContext context: Context): ManduinDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ManduinDatabase::class.java,
            "manduin_db"
        ).build()
    }
}