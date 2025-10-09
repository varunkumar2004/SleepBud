package com.varunkumar.myapplication.data.datasource

import com.varunkumar.myapplication.data.model.RawSensorData
import kotlinx.coroutines.flow.Flow

interface SensorDataSource {
    fun startTracking(): Flow<RawSensorData>
}