package com.example.eduskunta.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mpPersonNumber: Int,
    val isPositive: Boolean,
    val text: String,
    val date: String
)
