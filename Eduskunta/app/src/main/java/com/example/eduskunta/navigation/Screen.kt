package com.example.eduskunta.navigation

sealed class Screen(val route: String) {
    object MpList: Screen("mp_list")
    object MpDetail: Screen("mp_detail/{personNumber}") {
        fun createRoute(personNumber: Int) = "mp_Detail/$personNumber"
    }
}