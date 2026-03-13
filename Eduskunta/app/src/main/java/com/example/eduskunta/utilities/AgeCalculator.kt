package com.example.eduskunta.utilities

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun calculateAge(born: Int): Int {
    return try {
        LocalDate.now().year - born
    } catch (e: Exception){
        0
    }
}