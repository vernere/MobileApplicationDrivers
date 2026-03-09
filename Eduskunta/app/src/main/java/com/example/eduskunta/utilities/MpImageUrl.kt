package com.example.eduskunta.utilities

object MpImageUrl {

    private const val BASE_URL = "https://users.metropolia.fi/~peterh/edustajakuvat/"

    fun getUrl(firstname: String , lastname: String, personNumber: Int): String {
        val cleanFirst = firstname.trim()
        val cleanLast = lastname.trim()
        return "${BASE_URL}${cleanLast}${cleanFirst}-web-${personNumber}.jpg"
    }
}