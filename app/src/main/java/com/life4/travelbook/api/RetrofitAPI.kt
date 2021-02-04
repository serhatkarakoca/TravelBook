package com.life4.travelbook.api

import com.life4.travelbook.model.ImageResult
import com.life4.travelbook.util.Util.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("/api/")
    suspend fun searchImage(
        @Query("q") searchQuery: String,
        @Query("api") apiKey: String = API_KEY
    ): ImageResult


}