package com.varunkumar.myapplication.data.mappers

import com.varunkumar.myapplication.data.local.SleepSessionEntity
import com.varunkumar.myapplication.data.model.RawSensorData

fun RawSensorData.toEntity(): SleepSessionEntity {
    return SleepSessionEntity(
        timestamp = this.timestamp,
        accX = this.accX,
        accY = this.accY,
        accZ = this.accZ,
    )
}