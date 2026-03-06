package com.example.retrofit

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WikiApiService {
    @Headers("User-Agent: MyAgent/1.0")
    @GET("w/api.php")
    suspend fun getResponse(
        @Query("action") action: String = "query",
        @Query("format") format: String = "json",
        @Query("list") list: String = "search",
        @Query("srsearch") srsearch: String
    ): WikipediaResponse
}