package com.varunkumar.myapplication.presentation.screen.report

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.varunkumar.myapplication.data.local.entity.SleepFeatureEntity
import com.varunkumar.myapplication.domain.model.SleepReport

@Composable
fun ReportScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ReportViewModel = hiltViewModel<ReportViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val state = uiState) {
            is ReportState.Loading -> CircularProgressIndicator()
            is ReportState.Success -> {
                ReportContent(
                    navController = navController,
                    report = state.report
                )
            }
            is ReportState.Error -> Text("Error: ${state.message}")
        }
    }
}

@Composable
fun ReportContent(
    navController: NavController,
    report: SleepReport
) {
    if (report.features.isEmpty()) {
        Text("No sleep data available.")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Your Sleep Report", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        Text(text = report.sleepScore.toString(), style = MaterialTheme.typography.displayLarge)
        Text(text = "Sleep Score", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(32.dp))

        SleepStageChart(features = report.features)
        Spacer(modifier = Modifier.height(32.dp))

        // Key Stats
        Text(
            "Total Sleep: ${report.totalSleepDurationMinutes / 60}h ${report.totalSleepDurationMinutes % 60}m",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Deep Sleep: ${report.timeInDeepSleepMinutes}m")
        Text("Light Sleep: ${report.timeInLightSleepMinutes}m")
        Text("REM Sleep: ${report.timeInRemMinutes}m")
        Text("Awake: ${report.timeInWakeMinutes}m")
    }

    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = { navController.popBackStack() }) { // Go back to HomeScreen
        Text("Done")
    }
}

@Composable
fun SleepStageChart(
    features: List<SleepFeatureEntity>,
    modifier: Modifier = Modifier
) {
    val stageColors = mapOf(
        "Wake" to Color(0xFFE57373), // Red
        "Light Sleep (N1)" to Color(0xFFFFF176), // Yellow
        "Light Sleep (N2)" to Color(0xFF81C784), // Green
        "Deep Sleep" to Color(0xFF64B5F6), // Blue
        "REM" to Color(0xFFBA68C8), // Purple
        "Unknown" to Color.Gray
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.LightGray.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
    ) {
        if (features.isEmpty()) return@Canvas
        val blockWidth = size.width / features.size

        features.forEachIndexed { index, feature ->
            val stageColor = stageColors[feature.predictedStage] ?: Color.Gray

            drawRect(
                color = stageColor,
                topLeft = Offset(x = index * blockWidth, y = 0f),
                size = Size(width = blockWidth, height = size.height)
            )
        }
    }
}