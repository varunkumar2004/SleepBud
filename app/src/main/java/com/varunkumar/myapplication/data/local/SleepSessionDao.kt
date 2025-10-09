package com.varunkumar.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SleepSessionDao {
    @Insert
    suspend fun insert(sleepData: SleepSessionEntity)

    @Query("SELECT * FROM sleep_session_data")
    suspend fun getAll(): List<SleepSessionEntity>
}