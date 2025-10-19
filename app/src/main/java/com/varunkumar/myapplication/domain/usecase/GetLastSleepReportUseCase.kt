package com.varunkumar.myapplication.domain.usecase

import com.varunkumar.myapplication.data.repository.SleepRepository
import com.varunkumar.myapplication.domain.model.SleepReport
import javax.inject.Inject

class GetLastSleepReportUseCase @Inject constructor(
    private val repository: SleepRepository
) {
    suspend operator fun invoke(): SleepReport {
        val features = repository.getAllFeatures()

        if (features.isEmpty()) {
            return SleepReport() // Return an empty report if no data
        }

        var timeInWake = 0L
        var timeInLight = 0L
        var timeInDeep = 0L
        var timeInRem = 0L

        features.forEach {
            when {
                it.predictedStage?.contains("Wake") == true -> timeInWake += 30
                it.predictedStage?.contains("Light") == true -> timeInLight += 30
                it.predictedStage?.contains("Deep") == true -> timeInDeep += 30
                it.predictedStage?.contains("REM") == true -> timeInRem += 30
            }
        }

        val totalSleepSeconds = timeInLight + timeInDeep + timeInRem

        val sleepScore = ( (totalSleepSeconds / (8 * 3600f)) * 60 + // 60 points for 8 hours of sleep
                ((timeInDeep + timeInRem) / totalSleepSeconds.toFloat()) * 40 // 40 points for deep/REM percentage
                ).coerceIn(0f, 100f).toInt()


        return SleepReport(
            features = features,
            sleepScore = sleepScore,
            totalSleepDurationMinutes = totalSleepSeconds / 60,
            timeInWakeMinutes = timeInWake / 60,
            timeInLightSleepMinutes = timeInLight / 60,
            timeInDeepSleepMinutes = timeInDeep / 60,
            timeInRemMinutes = timeInRem / 60
        )
    }
}