package com.varunkumar.myapplication.data.repository

interface SleepRepository {
    suspend fun startTracking()
    suspend fun stopTracking()
}