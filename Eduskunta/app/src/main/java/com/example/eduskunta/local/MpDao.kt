package com.example.eduskunta.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MpDao {
    @Query(value = "SELECT * FROM mps ORDER BY lastname ASC")
    fun getAllMps(): Flow<List<MpEntity>>

    @Query(value = "SELECT * FROM mps WHERE party = :party ORDER BY lastname ASC ")
    fun getMpsByParty(party: String): Flow<List<MpEntity>>

    @Query(value = "SELECT * FROM mps WHERE constituency = :constituency ORDER BY lastname ASC")
    fun getMpsByConstituencies(constituency: String): Flow<List<MpEntity>>

    @Query(value = "SELECT * FROM mps WHERE personNumber = :personNumber")
    fun getMpByPersonNumber(personNumber: Int): Flow<MpEntity>

    @Upsert
    suspend fun upsertAll(mps: List<MpEntity>)

}