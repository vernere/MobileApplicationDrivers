package com.example.eduskunta.repository

import com.example.eduskunta.local.MpDao
import com.example.eduskunta.local.MpEntity
import com.example.eduskunta.mappers.toEntity
import com.example.eduskunta.network.MpApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MpRepository(
    private val mpDao: MpDao,
    private val apiService: MpApi
) {
    val mpsByParty: Flow<Map<String, List<MpEntity>>> = mpDao.getAllMps().map { mps ->
        mps.groupBy { it.party }
    }

    val mpsByConstituency: Flow<Map<String, List<MpEntity>>> = mpDao.getAllMps().map { mps ->
        mps.groupBy { it.constituency }
    }

    fun getMp(personNumber: Int) : Flow<MpEntity> = mpDao.getMpByPersonNumber(personNumber)

    suspend fun syncMaps() {
        try {
            val mps = apiService.getMps()
            android.util.Log.d("MpRepository", "Fetched ${mps.size} MPs")
            android.util.Log.d("MpRepository", "First picture: ${mps.firstOrNull()?.picture}")
            mpDao.upsertAll(mps.map { it.toEntity() })
        } catch (e: Exception) {
            android.util.Log.e("MpRepository", "Sync failed: ${e.message}")
            e.printStackTrace()
        }
    }
}