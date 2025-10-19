package com.varunkumar.myapplication.presentation.screen.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varunkumar.myapplication.domain.usecase.ProcessSleepSessionUseCase
import com.varunkumar.myapplication.domain.usecase.StartTrackingUseCase
import com.varunkumar.myapplication.domain.usecase.StopTrackingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(
    private val startTrackingUseCase: StartTrackingUseCase,
    private val stopTrackingUseCase: StopTrackingUseCase,
    private val processSleepSessionUseCase: ProcessSleepSessionUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TrackingState())
    val state = _state.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<Unit>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onStartTracking() {
        viewModelScope.launch {
            startTrackingUseCase()
            _state.update {
                it.copy(true)
            }
        }
    }

    fun onStopTracking() {
        viewModelScope.launch {
            stopTrackingUseCase()
            // Show a processing indicator on the tracking screen
            _state.update { it.copy(isTracking = false, isProcessing = true) }

            // Launch the heavy processing in a background job
            val processingJob = launch(Dispatchers.IO) {
                processSleepSessionUseCase()
            }

            // Wait for the processing job to complete before navigating
            processingJob.join()

            // Hide the processing indicator and tell the UI to navigate
            _state.update { it.copy(isProcessing = false) }
            _navigationEvent.emit(Unit)
        }
    }
}