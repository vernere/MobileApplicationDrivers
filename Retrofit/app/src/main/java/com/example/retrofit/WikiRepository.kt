package com.example.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WikiRepository {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://en.wikipedia.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(WikiApiService::class.java)

    suspend fun hitCountCheck(name: String) : WikipediaResponse {
        val response = api.getResponse(srsearch = name)
        return response
    }
}