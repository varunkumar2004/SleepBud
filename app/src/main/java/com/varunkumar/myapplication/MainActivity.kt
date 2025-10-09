package com.varunkumar.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.varunkumar.myapplication.presentation.screens.tracking.TrackingScreen
import com.varunkumar.myapplication.presentation.screens.tracking.TrackingState
import com.varunkumar.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {

                TrackingScreen()

            }
        }
    }
}