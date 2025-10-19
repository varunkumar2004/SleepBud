package com.varunkumar.myapplication

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.varunkumar.myapplication.presentation.navigation.Screen
import com.varunkumar.myapplication.presentation.screen.home.HomeScreen
import com.varunkumar.myapplication.presentation.screen.report.ReportScreen
import com.varunkumar.myapplication.presentation.screen.tracking.TrackingScreen
import com.varunkumar.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route
                ) {
                    composable(route = Screen.Home.route) {
                        HomeScreen(
                            modifier = Modifier.fillMaxSize(),
                            navController = navController
                        )
                    }

                    composable(route = Screen.Tracking.route) {
                        TrackingScreen(navController = navController)
                    }

                    composable(route = Screen.Report.route) {
                        ReportScreen(navController = navController)
                    }
                }

            }
        }
    }
}