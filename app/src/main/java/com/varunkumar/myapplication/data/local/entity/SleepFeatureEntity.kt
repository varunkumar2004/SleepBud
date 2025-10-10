package com.varunkumar.myapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_features")
data class SleepFeatureEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val windowTimestamp: Long,
    val motionVariance: Float,
    val rotationVariance: Float,
    val averageAmplitude: Float,
    val peakAmplitude: Int,
    val predictedStage: String? = null
)