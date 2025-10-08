package com.varunkumar.myapplication.data.datasource

import com.varunkumar.myapplication.data.model.RawSensorData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AndroidSensorDataSource: SensorDataSource {
    override fun startTracking(): Flow<RawSensorData> {
        return flow {

        }
    }
}