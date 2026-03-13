package com.example.eduskunta.utilities

object MpImageUrl {

    private const val BASE_URL = "https://users.metropolia.fi/~peterh/edustajakuvat/"

    fun getUrl(firstName: String, lastName: String, personNumber:Int): String {
       return "${BASE_URL}${lastName}-${firstName}-web-${personNumber}.jpg"
    }
}