package com.varunkumar.myapplication.data.local

import androidx.room.PrimaryKey

data class SleepSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val accX: Float,
    val accY: Float,
    val accZ: Float,
)