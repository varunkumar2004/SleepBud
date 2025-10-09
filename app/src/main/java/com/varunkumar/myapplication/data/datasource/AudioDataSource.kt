package com.varunkumar.myapplication.data.datasource

import kotlinx.coroutines.flow.Flow

interface AudioDataSource {
    fun startTracking(): Flow<Int>
}