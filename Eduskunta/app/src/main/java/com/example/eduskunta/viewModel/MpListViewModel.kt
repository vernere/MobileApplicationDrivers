package com.example.eduskunta.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.eduskunta.local.MpEntity
import com.example.eduskunta.repository.MpRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.emptyMap

enum class GroupBy { PARTY, CONSTITUENCY }

class MpListViewModel(
    private val mpRepository: MpRepository
): ViewModel() {
    private val _groupBy = MutableStateFlow(GroupBy.PARTY)
    val groupBy: StateFlow<GroupBy> = _groupBy.asStateFlow()

    val groupedMps: StateFlow<Map<String, List<MpEntity>>> = _groupBy
        .flatMapLatest { mode ->
            when(mode) {
                GroupBy.PARTY -> mpRepository.mpsByParty
                GroupBy.CONSTITUENCY -> mpRepository.mpsByConstituency
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyMap()
        )
    init {
        viewModelScope.launch {
            mpRepository.syncMaps()
        }
    }
    fun toggleGrouping(){
        _groupBy.update { current ->
            if (current == GroupBy.PARTY) GroupBy.CONSTITUENCY else GroupBy.PARTY
        }
    }
}

class MpListViewModelFactory(
    private val mpRepository: MpRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MpListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MpListViewModel(mpRepository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}