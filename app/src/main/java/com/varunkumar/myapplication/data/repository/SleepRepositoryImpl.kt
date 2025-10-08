package com.varunkumar.myapplication.data.repository

import com.varunkumar.myapplication.data.datasource.LocalDataSource
import com.varunkumar.myapplication.data.datasource.SensorDataSource
import com.varunkumar.myapplication.data.mappers.toEntity
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Singleton
class SleepRepositoryImpl @Inject constructor(
    private val sensorDataSource: SensorDataSource,
    private val localDataSource: LocalDataSource
) : SleepRepository {

    // a piece of work that can be actively controlled
    private var trackingJob: Job? = null

    override suspend fun startTracking() {
        trackingJob?.cancel() // safety measure -> prevent multiple redundant jobs

        trackingJob = CoroutineScope(Dispatchers.IO).launch {
            sensorDataSource.startTracking().collectLatest { rawData ->
                localDataSource.insertSleepSession(rawData.toEntity())
            }
        }
    }

    override suspend fun stopTracking() {
        trackingJob?.cancel()
        trackingJob = null
    }
}