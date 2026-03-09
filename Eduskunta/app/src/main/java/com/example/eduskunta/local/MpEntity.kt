package com.example.eduskunta.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mps")
data class MpEntity(
    @PrimaryKey val personNumber: Int,
    val firstName: String,
    val lastName: String,
    val party: String,
    val constituency: String,
    val twitter: String?,
    val bornYear: String,
    val seatNumber: Int?
)
