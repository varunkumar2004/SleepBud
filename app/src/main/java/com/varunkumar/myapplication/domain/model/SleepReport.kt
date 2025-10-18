package com.varunkumar.myapplication.domain.model

import com.varunkumar.myapplication.data.local.entity.SleepFeatureEntity

data class SleepReport(
    val features: List<SleepFeatureEntity> = emptyList(),
    val sleepScore: Int = 0,
    val totalSleepDurationMinutes: Long = 0,
    val timeInWakeMinutes: Long = 0,
    val timeInLightSleepMinutes: Long = 0,
    val timeInDeepSleepMinutes: Long = 0,
    val timeInRemMinutes: Long = 0
)