package com.example.eduskunta.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MpDto(
    val personNumber: Int,
    @Json(name = "first") val firstName: String,
    @Json(name = "last") val lastName: String,
    val party: String,
    val constituency: String,
    val twitter: String?,
    val bornYear: String,
    val seatNumber: Int?
    )