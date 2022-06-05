package com.bangkit.manduin.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bangkit.manduin.data.local.entity.PlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {

    @Query("SELECT * FROM place_bookmark ORDER BY name ASC")
    fun getAllBookmarkedPlace(): LiveData<List<PlaceEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToBookmark(place: PlaceEntity)

    @Delete
    suspend fun deletePlaceFromBookmark(place: PlaceEntity)

    @Query("DELETE FROM place_bookmark")
    suspend fun deleteAllBookmarkedPlace()

    @Query("SELECT EXISTS(SELECT * FROM place_bookmark WHERE place_id = :id)")
    fun isPlaceBookmarked(id: Int) : Flow<Boolean>
}