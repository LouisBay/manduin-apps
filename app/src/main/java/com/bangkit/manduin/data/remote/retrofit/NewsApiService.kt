package com.bangkit.manduin.data.remote.retrofit

import com.bangkit.manduin.data.remote.response.NewsResponse
import retrofit2.http.GET

interface NewsApiService {
    @GET("tribun/travel/")
    suspend fun getTravelNews() : NewsResponse
}