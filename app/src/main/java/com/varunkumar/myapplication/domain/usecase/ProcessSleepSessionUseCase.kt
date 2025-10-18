package com.varunkumar.myapplication.domain.usecase

import com.varunkumar.myapplication.data.local.entity.SleepFeatureEntity
import com.varunkumar.myapplication.data.repository.SleepRepository
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

class ProcessSleepSessionUseCase @Inject constructor(
    private val sleepRepository: SleepRepository,
    // 1. INJECT the ClassifyFeaturesUseCase in the constructor
    private val classifyFeaturesUseCase: ClassifyFeaturesUseCase
) {
    /**
     * Orchestrates the entire post-session data processing pipeline.
     */
    suspend operator fun invoke() {
        // Clear features from the previous night to start fresh.
        sleepRepository.clearFeatures()

        // Get all raw data from the session that just ended.
        val rawData = sleepRepository.getRawDataForLastSession()
        if (rawData.isEmpty()) return // Nothing to process.

        // Group the raw data into 30-second windows.
        val windows = rawData.groupBy { it.timestamp / 30000 }
        val features = mutableListOf<SleepFeatureEntity>()

        // Loop through each window and extract features.
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

        // Save the newly extracted features to the database.
        if (features.isNotEmpty()) {
            sleepRepository.saveFeatures(features)
        }
        classifyFeaturesUseCase()
        sleepRepository.clearFeatures()
    }

    /**
     * Helper function to calculate the variance of a list of floats.
     */
    private fun calculateVariance(data: List<Float>): Float {
        if (data.size < 2) return 0f
        val mean = data.average().toFloat()
        return data.map { (it - mean).pow(2) }.sum() / (data.size - 1)
    }
}