package com.varunkumar.myapplication.data.repository

import com.varunkumar.myapplication.data.datasource.AudioDataSource
import com.varunkumar.myapplication.data.datasource.LocalDataSource
import com.varunkumar.myapplication.data.datasource.SensorDataSource
import com.varunkumar.myapplication.data.local.entity.SleepFeatureEntity
import com.varunkumar.myapplication.data.local.entity.SleepSessionEntity
import com.varunkumar.myapplication.data.mappers.toEntity
import com.varunkumar.myapplication.data.model.RawSensorData
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Singleton
class SleepRepositoryImpl @Inject constructor(
    private val sensorDataSource: SensorDataSource, // for sensors
    private val audioDataSource: AudioDataSource, // for microphone
    private val localDataSource: LocalDataSource // for database
) : SleepRepository {
    // a piece of work that can be actively controlled
    private var trackingJob: Job? = null

    override suspend fun startTracking() {
        trackingJob?.cancel() // safety measure -> prevent multiple redundant jobs

        val sensorFlow = sensorDataSource.startTracking()
        val audioFlow = audioDataSource.startTracking()

        trackingJob = combine(sensorFlow, audioFlow) { sensorReading, audioReading ->
            RawSensorData(
                timestamp = System.currentTimeMillis(),
                accX = sensorReading.accX,
                accY = sensorReading.accY,
                accZ = sensorReading.accZ,
                gyroX = sensorReading.gyroX,
                gyroY = sensorReading.gyroY,
                gyroZ = sensorReading.gyroZ,
                audioAmplitude = audioReading
            )
        }.onEach { combinedData ->
            localDataSource.insertSleepData(combinedData.toEntity())
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    override suspend fun stopTracking() {
        trackingJob?.cancel()
        trackingJob = null
    }

    override suspend fun updateFeatures(features: List<SleepFeatureEntity>) {
        localDataSource.updateSleepFeatures(features) // Call a new function in LocalDataSource
    }

    override suspend fun getRawDataForLastSession(): List<SleepSessionEntity> {
        return localDataSource.getAllRawSleepData()
    }

    override suspend fun saveFeatures(features: List<SleepFeatureEntity>) {
        localDataSource.insertSleepFeatures(features)
    }

    override suspend fun clearRawData() {
        localDataSource.clearAllRawSleepData()
    }

    override suspend fun clearFeatures() {
        localDataSource.clearAllFeatures()
    }

    override suspend fun getAllFeatures(): List<SleepFeatureEntity> {
        return localDataSource.getAllFeatures()
    }
}