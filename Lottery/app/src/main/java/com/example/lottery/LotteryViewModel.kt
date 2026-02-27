package com.example.lottery


import androidx.compose.runtime.currentComposer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LotteryState(
    val selectedNumbers: Set<Int> = emptySet(),
    val drawnNumbers: List<Int> = emptyList(),
    val matchCount: Int? = null,
    val hasPlayed: Boolean = false
)


class LotteryViewModel : ViewModel() {

    private val _state = MutableStateFlow(LotteryState())
    val state: StateFlow<LotteryState> = _state.asStateFlow()

    fun toggleNumber(number: Int) {
        _state.update { current ->
            val selected = current.selectedNumbers.toMutableSet()
            if (selected.contains(number)) {
                selected.remove(number)
            } else {
                if (selected.size >= 7) return
                selected.add(number)
            }
            current.copy(selectedNumbers = selected)
        }
    }

    fun play() {
        val selected = _state.value.selectedNumbers
        if (selected.size != 7) return

        val drawn = (1..40).toList().shuffled().take(7)
        val matches = selected.intersect(drawn.toSet()).size

        _state.update {
            it.copy(
                drawnNumbers = drawn.sorted(),
                matchCount = matches,
                hasPlayed = true
            )
        }
    }

    fun reset() {
        _state.value = LotteryState()
    }
}
