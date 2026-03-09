package com.example.eduskunta.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface ReviewDao {
    @Insert
    suspend fun insertReview(review: ReviewEntity)

    @Query(value = "SELECT * FROM reviews WHERE mpPersonNumber = :mpPersonNumber ORDER BY date DESC" )
    fun getReviewsByPersonNumber(mpPersonNumber: Int): Flow<List<ReviewEntity>>

}