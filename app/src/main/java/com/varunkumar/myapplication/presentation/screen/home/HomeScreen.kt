package com.varunkumar.myapplication.presentation.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.varunkumar.myapplication.presentation.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("SleepBud", style = MaterialTheme.typography.displayMedium)
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate(Screen.Tracking.route) },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Start New Session")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate(Screen.Report.route) },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("View Last Report")
        }
    }
}