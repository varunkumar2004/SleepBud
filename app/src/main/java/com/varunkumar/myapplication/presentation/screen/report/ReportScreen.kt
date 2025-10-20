package com.varunkumar.myapplication.presentation.screen.report

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.varunkumar.myapplication.data.local.entity.SleepFeatureEntity
import com.varunkumar.myapplication.domain.model.SleepReport
import com.varunkumar.myapplication.presentation.components.ButtonView

@Composable
fun ReportScreen(
    modifier: Modifier = Modifier,
    viewModel: ReportViewModel = hiltViewModel<ReportViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier,
    ) {
        when (val state = uiState) {
            is ReportState.Loading -> CircularProgressIndicator()

            is ReportState.Success -> {
                ActiveReportContent(
                    report = state.report
                )
            }

            is ReportState.Error -> {
                ButtonView(
                    text = "Error: ${state.message}",
                    padding = 40.dp,
                    cornerShape = 2000.dp,
                    backgroundColor = MaterialTheme.colorScheme.inversePrimary,
                    isEnabled = false
                )
            }
        }
    }
}

@Composable
private fun ActiveReportContent(
    report: SleepReport
) {
    if (report.features.isEmpty()) {
        ButtonView(
            text = "No records available.",
            padding = 40.dp,
            cornerShape = 2000.dp,
            backgroundColor = MaterialTheme.colorScheme.inversePrimary,
            isEnabled = false
        )

        return
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(40.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column {
                    Text(
                        text = "Sleep Report",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        text = "October 20, 2025",
                        fontStyle = FontStyle.Italic
                    )
                }

                StatsContainer(
                    modifier = Modifier,
                    sleepReport = report
                )
            }

            ScoreContainer(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.tertiaryContainer),
                score = report.sleepScore.toString()
            )
        }
    }
}

@Composable
private fun ScoreContainer(
    modifier: Modifier = Modifier,
    score: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = score,
            style = MaterialTheme.typography.displayMedium
        )

        Text(
            text = "Score",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun StatsContainer(
    modifier: Modifier = Modifier,
    sleepReport: SleepReport
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatView(
            key = "Total Sleep",
            value = "${sleepReport.totalSleepDurationMinutes / 60}h " +
                    "${sleepReport.totalSleepDurationMinutes % 60}m"
        )

        StatView(
            key = "Deep Sleep",
            value = "${sleepReport.timeInDeepSleepMinutes}m"
        )

        StatView(
            key = "Light Sleep",
            value = "${sleepReport.timeInLightSleepMinutes}m"
        )

        StatView(
            key = "REM Sleep",
            value = "${sleepReport.timeInRemMinutes}m"
        )

        StatView(
            key = "Awake",
            value = "${sleepReport.timeInWakeMinutes}m"
        )
    }
}

@Composable
private fun StatView(
    modifier: Modifier = Modifier,
    key: String,
    value: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(key)

        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.onTertiary)
                .padding(horizontal = 5.dp),
            text = value
        )
    }
}


