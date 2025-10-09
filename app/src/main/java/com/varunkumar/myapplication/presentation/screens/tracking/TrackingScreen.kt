package com.varunkumar.myapplication.presentation.screens.tracking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TrackingScreen(
    viewModel: TrackingViewModel = hiltViewModel<TrackingViewModel>()
) {
    // Observe the state from the ViewModel
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Button changes based on the 'isTracking' state
        if (uiState.isTracking) {
            Button(onClick = { viewModel.onStopTracking() }) {
                Text("Stop Tracking")
            }
        } else {
            Button(onClick = { viewModel.onStartTracking() }) {
                Text("Start Tracking")
            }
        }

        // Display the current status
        Text(
            text = if (uiState.isTracking) "Status: Collecting data..." else "Status: Idle",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}