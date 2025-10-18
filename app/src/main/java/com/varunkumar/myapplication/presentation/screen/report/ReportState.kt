package com.varunkumar.myapplication.presentation.screen.report

import com.varunkumar.myapplication.domain.model.SleepReport

sealed interface ReportState {
    object Loading : ReportState
    data class Success(val report: SleepReport) : ReportState
    data class Error(val message: String) : ReportState
}