package com.bangkit.manduin.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bangkit.manduin.data.local.entity.PlaceEntity

@Database(
    entities = [PlaceEntity::class],
    version = 1,
    exportSchema = false
)

abstract class ManduinDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}