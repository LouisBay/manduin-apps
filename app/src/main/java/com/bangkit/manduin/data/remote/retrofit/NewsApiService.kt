package com.bangkit.manduin.data.remote.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("tribun/travel/")
    suspend fun getTravelNews() : NewsResponse
}