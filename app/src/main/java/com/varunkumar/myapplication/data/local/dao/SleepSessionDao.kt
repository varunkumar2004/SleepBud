package com.varunkumar.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.varunkumar.myapplication.data.local.entity.SleepSessionEntity

@Dao
interface SleepSessionDao {
    @Insert
    suspend fun insert(sleepData: SleepSessionEntity)

    @Query("SELECT * FROM sleep_session_data")
    suspend fun getAll(): List<SleepSessionEntity>

    @Query("DELETE FROM sleep_session_data")
    suspend fun clearAll()
}