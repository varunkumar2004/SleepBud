package com.varunkumar.myapplication.domain.usecase

import com.varunkumar.myapplication.data.repository.SleepRepository
import javax.inject.Inject

class StopTrackingUseCase @Inject constructor(
    private val sleepRepository: SleepRepository
) {
    suspend operator fun invoke() {
        sleepRepository.stopTracking()
    }
}