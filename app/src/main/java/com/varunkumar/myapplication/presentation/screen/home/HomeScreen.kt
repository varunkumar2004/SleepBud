package com.varunkumar.myapplication.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.varunkumar.myapplication.presentation.components.ApplicationTopBar
import com.varunkumar.myapplication.presentation.components.ButtonView
import com.varunkumar.myapplication.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Scaffold(
        modifier = modifier,
        topBar = { ApplicationTopBar() }
    ) { innerPadding ->
        ButtonsContainer(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(16.dp),
            onViewReportClick = { navController.navigate(Screen.Report.route) },
            onStartSleepSessionClick = { },
            onChangeSleepSessionSettingsClick = { }
        )
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
            onClick = { onViewReportClick() }
        )

        ButtonView(
            modifier = Modifier.weight(1f),
            text = "Start\nSleep Session",
            padding = 20.dp,
            cornerShape = 200.dp,
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
            onClick = { onStartSleepSessionClick() }
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

@Preview
@Composable
private fun HomeScreenPrev() {
    val navController = rememberNavController()
    HomeScreen(
        navController = navController
    )
}