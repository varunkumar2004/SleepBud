package com.varunkumar.myapplication.presentation.screens.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varunkumar.myapplication.domain.usecase.StartTrackingUseCase
import com.varunkumar.myapplication.domain.usecase.StopTrackingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(
    private val startTrackingUseCase: StartTrackingUseCase,
    private val stopTrackingUseCase: StopTrackingUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TrackingState())
    val state = _state.asStateFlow()

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
            _state.update {
                it.copy(false)
            }
        }
    }
}