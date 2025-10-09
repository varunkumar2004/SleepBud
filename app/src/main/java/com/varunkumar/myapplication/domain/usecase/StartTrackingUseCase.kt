package com.varunkumar.myapplication.domain.usecase

import com.varunkumar.myapplication.data.repository.SleepRepository
import jakarta.inject.Inject

class StartTrackingUseCase @Inject constructor(
    private val sleepRepository: SleepRepository
) {
    suspend operator fun invoke() {
        sleepRepository.startTracking()
    }
}