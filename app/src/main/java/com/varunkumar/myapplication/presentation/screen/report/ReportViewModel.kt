package com.varunkumar.myapplication.presentation.screen.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varunkumar.myapplication.domain.usecase.GetLastSleepReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getLastSleepReportUseCase: GetLastSleepReportUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ReportState>(ReportState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadReport()
    }

    private fun loadReport() {
        viewModelScope.launch {
            _uiState.value = ReportState.Loading

            try {
                val report = getLastSleepReportUseCase()
                _uiState.value = ReportState.Success(report)
            } catch (e: Exception) {
                _uiState.value = ReportState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}