package com.bangkit.manduin.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class LandmarkResponse(

	@field:SerializedName("data")
	val data: ArrayList<LandmarkItem>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

@Parcelize
data class LandmarkItem(

	@field:SerializedName("land_id")
	val landId: Int,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("rating")
	val rating: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("lat")
	val lat: Double,

	@field:SerializedName("lon")
	val lon: Double,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("Img_Url")
	val imgUrl: String,
) : Parcelable
