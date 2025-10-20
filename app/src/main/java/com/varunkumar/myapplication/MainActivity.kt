package com.varunkumar.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.varunkumar.myapplication.presentation.components.ApplicationTopBar
import com.varunkumar.myapplication.presentation.navigation.Screen
import com.varunkumar.myapplication.presentation.screen.home.HomeScreen
import com.varunkumar.myapplication.presentation.screen.report.ReportScreen
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

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { ApplicationTopBar() }
                ) { innerPadding ->

                    val screenModifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .clip(RoundedCornerShape(30.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .padding(all = 16.dp)

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route
                    ) {
                        composable(route = Screen.Home.route) {
                            HomeScreen(
                                modifier = screenModifier,
                                navController = navController
                            )
                        }

                        composable(route = Screen.Report.route) {
                            ReportScreen(
                                modifier = screenModifier
                            )
                        }
                    }

                }
            }
        }
    }
}