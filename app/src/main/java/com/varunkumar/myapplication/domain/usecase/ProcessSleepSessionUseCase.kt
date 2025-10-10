package com.varunkumar.myapplication.domain.usecase

import com.varunkumar.myapplication.data.local.entity.SleepFeatureEntity
import com.varunkumar.myapplication.data.repository.SleepRepository
import com.varunkumar.myapplication.utils.calculateVariance
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

class ProcessSleepSessionUseCase @Inject constructor(
    private val sleepRepository: SleepRepository
) {
    suspend operator fun invoke() {
        sleepRepository.clearFeatures()

        val rawData = sleepRepository.getRawDataForLastSession()
        if (rawData.isEmpty()) return

        val windows = rawData.groupBy { it.timestamp / 30000 }
        val features = mutableListOf<SleepFeatureEntity>()

        // This loop now ONLY calculates features and adds them to the list.
        for ((_, windowData) in windows) {
            if (windowData.isEmpty()) continue
            val motionMagnitudes = windowData.map { sqrt(it.accX.pow(2) + it.accY.pow(2) + it.accZ.pow(2)) }
            val rotationMagnitudes = windowData.map { sqrt(it.gyroX.pow(2) + it.gyroY.pow(2) + it.gyroZ.pow(2)) }

            val motionVariance = calculateVariance(motionMagnitudes)
            val rotationVariance = calculateVariance(rotationMagnitudes)
            val avgAmplitude = windowData.map { it.audioAmplitude }.average().toFloat()
            val peakAmplitude = windowData.maxOfOrNull { it.audioAmplitude } ?: 0

            features.add(
                SleepFeatureEntity(
                    windowTimestamp = windowData.first().timestamp,
                    motionVariance = motionVariance,
                    rotationVariance = rotationVariance,
                    averageAmplitude = avgAmplitude,
                    peakAmplitude = peakAmplitude
                )
            )
        }

        // After the loop is finished, save the complete list and clear the raw data.
        sleepRepository.saveFeatures(features)
        sleepRepository.clearRawData()
    }
}