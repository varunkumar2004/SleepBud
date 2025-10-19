package com.varunkumar.myapplication.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varunkumar.myapplication.domain.usecase.ProcessSleepSessionUseCase
import com.varunkumar.myapplication.domain.usecase.StartTrackingUseCase
import com.varunkumar.myapplication.domain.usecase.StopTrackingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val startTrackingUseCase: StartTrackingUseCase,
    private val stopTrackingUseCase: StopTrackingUseCase,
    private val processSleepSessionUseCase: ProcessSleepSessionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<Unit>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onStartTracking() {
        viewModelScope.launch {
            startTrackingUseCase()
            _uiState.update { it.copy(isTracking = true) }
        }
    }

    fun onStopTracking() {
        viewModelScope.launch {
            stopTrackingUseCase()
            _uiState.update { it.copy(isTracking = false, isProcessing = true) }

            val processingJob = launch(Dispatchers.IO) {
                processSleepSessionUseCase()
            }

            processingJob.join() // Wait for processing to finish

            _uiState.update { it.copy(isProcessing = false) }
            _navigationEvent.emit(Unit) // Navigate after processing
        }
    }
}