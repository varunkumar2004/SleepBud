package com.varunkumar.myapplication.data.local

import androidx.room.Insert

interface SleepSessionDao {
    @Insert
    suspend fun insert(sleepData: SleepSessionEntity)
}