package com.varunkumar.myapplication.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Report : Screen("report_screen")
}