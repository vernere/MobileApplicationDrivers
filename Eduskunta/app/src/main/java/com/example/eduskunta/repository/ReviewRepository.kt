package com.example.eduskunta.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.eduskunta.local.ReviewDao
import com.example.eduskunta.local.ReviewEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ReviewRepository(
    private val reviewDao: ReviewDao
) {
    fun getReviewsForMp(personNumber: Int): Flow<List<ReviewEntity>> =
        reviewDao.getReviewsByPersonNumber(personNumber)


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addReview(
        mpPersonNumber: Int,
        isPositive: Boolean,
        text: String
    ) {
        val review = ReviewEntity(
            mpPersonNumber = mpPersonNumber,
            isPositive = isPositive,
            text = text,
            date = LocalDate.now().format(DateTimeFormatter.ofPattern("d.M.yyyy"))
        )
        reviewDao.insertReview(review)
    }
}