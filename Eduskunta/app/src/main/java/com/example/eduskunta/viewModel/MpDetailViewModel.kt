package com.example.eduskunta.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.eduskunta.local.MpEntity
import com.example.eduskunta.local.ReviewEntity
import com.example.eduskunta.repository.MpRepository
import com.example.eduskunta.repository.ReviewRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MpDetailViewModel(
    private val mpRepository: MpRepository,
    private val reviewRepository: ReviewRepository,
    private val personNumber: Int
): ViewModel() {
    val mp: StateFlow<MpEntity?> = mpRepository.getMp(personNumber)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val reviews: StateFlow<List<ReviewEntity>> = reviewRepository
        .getReviewsForMp(personNumber)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    @RequiresApi(Build.VERSION_CODES.O)
    fun addReview(isPositive: Boolean, text: String) {
        viewModelScope.launch {
            reviewRepository.addReview(
                mpPersonNumber = personNumber,
                isPositive = isPositive,
                text = text
            )
        }
    }
}

class MpDetailViewModelFactory(
    private val mpRepository: MpRepository,
    private val reviewRepository: ReviewRepository,
    private val personNumber: Int
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MpDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MpDetailViewModel(mpRepository, reviewRepository, personNumber) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}