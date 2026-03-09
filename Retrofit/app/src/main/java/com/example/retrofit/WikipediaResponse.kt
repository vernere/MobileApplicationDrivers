package com.example.retrofit

data class searchinfo(val totalhits: Int)
data class query(val searchinfo: searchinfo)

data class WikipediaResponse(val query: query)
