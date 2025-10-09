package com.varunkumar.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_session_data")
data class SleepSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val accX: Float,
    val accY: Float,
    val accZ: Float,
    val audioAmplitude: Int
)