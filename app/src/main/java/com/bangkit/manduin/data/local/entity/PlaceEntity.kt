package com.bangkit.manduin.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "place_bookmark")
data class PlaceEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "place_id")
    val placeId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "province")
    val province: String,

    @ColumnInfo(name = "rating")
    val rating: Int? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "lat")
    val lat: Double,

    @ColumnInfo(name = "lon")
    val lon: Double,

    @ColumnInfo(name = "img_url")
    val imgUrl: String,

    @ColumnInfo(name = "category")
    val category: String? = null
) : Parcelable