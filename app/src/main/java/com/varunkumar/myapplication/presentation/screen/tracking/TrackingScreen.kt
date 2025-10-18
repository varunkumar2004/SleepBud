package com.varunkumar.myapplication.presentation.screen.tracking

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect {
            navController.navigate(Screen.Report.route) {
                popUpTo(Screen.Tracking.route) {
                    inclusive = true
                }
            }
        }
    }

    val audioPermissionState = rememberPermissionState(
        permission = Manifest.permission.RECORD_AUDIO
    )

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (audioPermissionState.status.isGranted) {
            TrackingContent(
                isTracking = uiState.isTracking,
                onStartClick = { viewModel.onStartTracking() },
                onStopClick = { viewModel.onStopTracking() }
            )
        } else {
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
    onStartClick: () -> Unit,
    onStopClick: () -> Unit
) {
    if (isTracking) {
        Button(onClick = onStopClick) {
            Text("Stop Tracking")
        }
    } else {
        Button(onClick = onStartClick) {
            Text("Start Tracking")
        }
    }

    Text(
        text = if (isTracking) "Status: Collecting data..." else "Status: Idle",
        modifier = Modifier.padding(top = 16.dp)
    )
}

@Composable
fun PermissionRequestContent(
    onRequestPermission: () -> Unit
) {
    Text(
        text = "Microphone access is required to analyze your sleep by detecting sounds like snoring.",
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(16.dp)
    )

    Button(onClick = onRequestPermission) {
        Text("Grant Permission")
    }
}