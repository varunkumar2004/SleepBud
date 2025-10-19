package com.varunkumar.myapplication.presentation.screen.tracking

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.varunkumar.myapplication.presentation.navigation.Screen

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TrackingScreen(
    navController: NavController,
    viewModel: TrackingViewModel = hiltViewModel()
) {
    // 1. Create a permission state holder for the audio permission.
    val audioPermissionState = rememberPermissionState(
        permission = Manifest.permission.RECORD_AUDIO
    )

    // 2. Observe the UI state from the ViewModel.
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    // 3. Listen for one-time navigation events from the ViewModel.
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect {
            navController.navigate(Screen.Report.route) {
                // Clear the tracking screen from the back stack so the user can't go back to it.
                popUpTo(Screen.Tracking.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 4. Check if the audio permission has been granted.
        if (audioPermissionState.status.isGranted) {
            // If permission is granted, show the main tracking UI.
            TrackingContent(
                isTracking = uiState.isTracking,
                isProcessing = uiState.isProcessing,
                onStartClick = { viewModel.onStartTracking() },
                onStopClick = { viewModel.onStopTracking() }
            )
        } else {
            // If permission is not granted, show the rationale and request button.
            PermissionRequestContent(
                onRequestPermission = {
                    audioPermissionState.launchPermissionRequest()
                }
            )
        }
    }
}

@Composable
fun TrackingContent(
    isTracking: Boolean,
    isProcessing: Boolean,
    onStartClick: () -> Unit,
    onStopClick: () -> Unit
) {
    if (isProcessing) {
        // Show a loading indicator while the data is being processed.
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Text("Analyzing your sleep...")
    } else {
        // Show the appropriate button based on the tracking state.
        if (isTracking) {
            Button(onClick = onStopClick) {
                Text("Stop Tracking")
            }
        } else {
            Button(onClick = onStartClick) {
                Text("Start Tracking")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = if (isTracking) "Status: Collecting data..." else "Status: Idle")
    }
}

@Composable
fun PermissionRequestContent(
    onRequestPermission: () -> Unit
) {
    // This UI explains why the permission is needed.
    Text(
        text = "Microphone access is required to analyze your sleep by detecting sounds like snoring.",
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(16.dp)
    )
    Button(onClick = onRequestPermission) {
        Text("Grant Permission")
    }
}