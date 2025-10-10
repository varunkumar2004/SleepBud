package com.varunkumar.myapplication.data.model

data class RawSensorData(
    val timestamp: Long,
    val accX: Float,
    val accY: Float,
    val accZ: Float,
    val gyroX: Float,
    val gyroY: Float,
    val gyroZ: Float,
    val audioAmplitude: Int
)