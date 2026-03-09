package com.example.eduskunta.network

import retrofit2.http.GET

interface MpApi {
    @GET("mps.json")
    suspend fun getMps(): List<MpDto>
}