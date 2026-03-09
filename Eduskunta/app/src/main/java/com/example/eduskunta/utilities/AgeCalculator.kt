package com.example.eduskunta.utilities

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun calculateAge(born: String): Int {
    return try {
        val birthYear = born.take(4).toInt()
        LocalDate.now().year - birthYear
    } catch (e: Exception){
        0
    }
}