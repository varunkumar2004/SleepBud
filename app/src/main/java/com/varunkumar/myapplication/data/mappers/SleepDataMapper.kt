package com.varunkumar.myapplication.data.mappers

import com.varunkumar.myapplication.data.local.entity.SleepSessionEntity
import com.varunkumar.myapplication.data.model.RawSensorData

fun RawSensorData.toEntity(): SleepSessionEntity {
    return SleepSessionEntity(
        timestamp = this.timestamp,
        accX = this.accX,
        accY = this.accY,
        accZ = this.accZ,
        gyroX = this.gyroX,
        gyroY = this.gyroY,
        gyroZ = this.gyroZ,
        audioAmplitude = this.audioAmplitude
    )
}