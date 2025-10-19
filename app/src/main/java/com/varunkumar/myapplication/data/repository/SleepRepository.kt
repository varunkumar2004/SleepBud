package com.varunkumar.myapplication.data.repository

import com.varunkumar.myapplication.data.local.entity.SleepFeatureEntity
import com.varunkumar.myapplication.data.local.entity.SleepSessionEntity

interface SleepRepository {
    suspend fun startTracking()
    suspend fun stopTracking()

    suspend fun updateFeatures(features: List<SleepFeatureEntity>)
    suspend fun getRawDataForLastSession(): List<SleepSessionEntity>
    suspend fun saveFeatures(features: List<SleepFeatureEntity>)
    suspend fun clearRawData()
    suspend fun clearFeatures()

    suspend fun getAllFeatures(): List<SleepFeatureEntity>
}