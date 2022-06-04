package com.bangkit.manduin.data.remote.retrofit

import com.bangkit.manduin.data.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path

interface ManduinApiService {
    @GET("/landmark")
    suspend fun getAllLandmark() : LandmarkResponse

    @GET("/landmark/{id}")
    suspend fun getLandmarkDetail(
        @Path("id") id: Int
    ) : LandmarkItem?

    @GET("/wisata/{id}")
    suspend fun getTourismPlaceDetail(
        @Path("id") id: Int
    ) : TourismPlaceItem?

    @GET("/landmark/{label}/wisata")
    suspend fun getNearestTourismLocFromLandmark(
        @Path("label") label: String
    ) : WisataMapsResponse

    @GET("/provinsi/{search}")
    suspend fun getTourismPlaceAtProvince(
        @Path("search") search: String
    ) : TourismPlaceResponse
}