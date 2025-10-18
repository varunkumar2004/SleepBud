package com.varunkumar.myapplication.domain.usecase

import com.varunkumar.myapplication.data.repository.SleepRepository
import com.varunkumar.myapplication.ml.SleepClassifier
import javax.inject.Inject

class ClassifyFeaturesUseCase @Inject constructor(
    private val sleepRepository: SleepRepository,
    private val sleepClassifier: SleepClassifier
) {
    private val stageMap = mapOf(
        0 to "Wake",
        1 to "Light Sleep", // Simplified labels
        2 to "Light Sleep",
        3 to "Deep Sleep",
        4 to "REM"
    )

    suspend operator fun invoke() {
        val features = sleepRepository.getAllFeatures()
        if (features.isEmpty()) return

        val updatedFeatures = features.map { feature ->
            // 2. Use the classifier to get a prediction
            val predictedIndex = sleepClassifier.classify(feature.motionVariance)
            val predictedStageLabel = stageMap[predictedIndex] ?: "Unknown"

            feature.copy(predictedStage = predictedStageLabel)
        }

        sleepRepository.saveFeatures(updatedFeatures)
    }
}