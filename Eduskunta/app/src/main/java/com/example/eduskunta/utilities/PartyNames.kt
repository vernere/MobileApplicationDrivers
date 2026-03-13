package com.example.eduskunta.utilities

object PartyNames {
    fun getFullName(abbreviation: String): String = when (abbreviation.lowercase()) {
        "kok" -> "Kookomus"
        "sd" -> "Suomen Sosiaalidemokraattinen puolue"
        "ps" -> "Perussuomalaiset"
        "kesk" -> "Keskusta"
        "vihr" -> "Vihreät"
        "vas" -> "Vasemmistoliitto"
        "r" -> "Suomen ruotsalainen kansanpuolue"
        "kd" -> "Kristillisdemokraatit"
        "liik" -> "Liike Nyt"
        else -> abbreviation
    }
}