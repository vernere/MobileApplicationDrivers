package com.example.game

import androidx.compose.runtime.MutableIntState

class YliAliPeli {

    fun calculateGuess(startingNumber: Int, guess: Int): String {
        return when {
            guess == startingNumber -> "Correct"
            guess < startingNumber -> "Too low"
            else -> "Too high"
        }
    }
}