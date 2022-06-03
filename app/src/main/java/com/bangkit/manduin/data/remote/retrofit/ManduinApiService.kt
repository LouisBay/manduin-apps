package com.bangkit.manduin.data.remote.retrofit

import com.bangkit.manduin.data.remote.response.LandmarkItem
import com.bangkit.manduin.data.remote.response.LandmarkResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ManduinApiService {
    @GET("/landmark")
    suspend fun getAllLandmark() : LandmarkResponse

    @GET("/landmark/{id}")
    suspend fun getLandmarkDetail(
        @Path("id") id: Int
    ) : LandmarkItem?
}