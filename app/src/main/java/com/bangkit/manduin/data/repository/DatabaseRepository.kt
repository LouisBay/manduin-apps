package com.bangkit.manduin.data.repository


import com.bangkit.manduin.data.local.entity.PlaceEntity
import com.bangkit.manduin.data.local.room.ManduinDatabase
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    private val manduinDatabase: ManduinDatabase
) {
    fun getAllBookmarkedPlace() = manduinDatabase.placeDao().getAllBookmarkedPlace()

    fun checkBookmarked(id: Int) = manduinDatabase.placeDao().isPlaceBookmarked(id)

    suspend fun insertPlaceToBookmark(place: PlaceEntity) {
        manduinDatabase.placeDao().insertToBookmark(place)
    }

    suspend fun deletePlaceFromBookmark(place: PlaceEntity) {
        manduinDatabase.placeDao().deletePlaceFromBookmark(place)
    }

    suspend fun deleteAllBookmarkedPlace() {
        manduinDatabase.placeDao().deleteAllBookmarkedPlace()
    }
}