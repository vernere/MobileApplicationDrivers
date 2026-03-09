package com.example.yliali

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GuessingState(
    val generatedNumber: Int? = null,
    var guess: Int? = null,
    val result: GuessResult = GuessResult.NONE
)

enum class GuessResult {
    NONE, CORRECT, TOO_HIGH, TOO_LOW
}

class YliAliViewModel : ViewModel() {

    private val _state = MutableStateFlow(GuessingState())

    val state: StateFlow<GuessingState> = _state.asStateFlow()

    fun start() {

        if (_state.value.generatedNumber == null) {
            _state.update { current ->
                current.copy(generatedNumber = (1..10).random())
            }
        }
    }

    fun guess(input: Int?) {
        val target = _state.value.generatedNumber
        if (target == null || input == null) {
            return
        }
        val result = when {
            input == target -> GuessResult.CORRECT
            input > target -> GuessResult.TOO_HIGH
            else -> GuessResult.TOO_LOW
        }
        _state.update { current ->
            current.copy(guess = input, result = result)
        }
    }

    fun reset() {
        _state.value = GuessingState(generatedNumber = (1..10).random())
    }
}