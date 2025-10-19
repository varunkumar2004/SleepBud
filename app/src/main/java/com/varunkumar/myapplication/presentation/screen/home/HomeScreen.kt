package com.varunkumar.myapplication.presentation.screen.home

import android.Manifest
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.varunkumar.myapplication.domain.usecase.StopTrackingUseCase
import com.varunkumar.myapplication.presentation.components.ApplicationTopBar
import com.varunkumar.myapplication.presentation.components.ButtonView
import com.varunkumar.myapplication.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
) {
    val audioPermissionState = rememberPermissionState(
        permission = Manifest.permission.RECORD_AUDIO
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect {
            navController.navigate(Screen.Report.route)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = { ApplicationTopBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (audioPermissionState.status.isGranted) {
                AnimatedContent(uiState) {
                    when {
                        it.isTracking -> {
                            StopTrackingContainer(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = viewModel::onStopTracking
                            )
                        }

                        it.isProcessing -> {
                            TrackingProgressContainer(modifier = Modifier.fillMaxWidth())
                        }

                        else -> {
                            ButtonsContainer(
                                onViewReportClick = { navController.navigate(Screen.Report.route) },
                                onStartSleepSessionClick = viewModel::onStartTracking,
                                onChangeSleepSessionSettingsClick = { }
                            )
                        }
                    }
                }
            } else {
                PermissionRationaleContainer(
                    onClick = { audioPermissionState.launchPermissionRequest() }
                )
            }
        }
    }
}

@Composable
private fun ButtonsContainer(
    modifier: Modifier = Modifier,
    onViewReportClick: () -> Unit,
    onStartSleepSessionClick: () -> Unit,
    onChangeSleepSessionSettingsClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ButtonView(
            text = "View Reports",
            padding = 40.dp,
            cornerShape = 2000.dp,
            backgroundColor = MaterialTheme.colorScheme.inversePrimary,
            onClick = onViewReportClick
        )

        ButtonView(
            modifier = Modifier.weight(1f),
            text = "Start\nSleep Session",
            padding = 20.dp,
            cornerShape = 200.dp,
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            onClick = onStartSleepSessionClick
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onChangeSleepSessionSettingsClick() },
            text = "Sleep Sessions are detected automatically.\n" +
                    "You can change it here.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun StopTrackingContainer(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        ButtonView(
            modifier = Modifier
                .weight(1f),
            text = "Stop\nSleep Session",
            padding = 20.dp,
            cornerShape = 200.dp,
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            onClick = onClick
        )
    }
}

@Composable
fun TrackingProgressContainer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        ButtonView(
            modifier = Modifier.weight(1f),
            text = "Analyzing your sleep...",
            padding = 20.dp,
            cornerShape = 200.dp,
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            isEnabled = false
        )
    }
}

@Composable
private fun PermissionRationaleContainer(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Microphone access is required to analyze sleep sounds.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )

        ButtonView(
            modifier = Modifier.weight(1f),
            text = "Grant Permission",
            padding = 40.dp,
            cornerShape = 200.dp,
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            onClick = onClick
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPrev() {
    val navController = rememberNavController()
    HomeScreen(
        navController = navController
    )
}