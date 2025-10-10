package com.varunkumar.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.varunkumar.myapplication.data.local.entity.SleepFeatureEntity

@Dao
interface SleepFeatureDao {
    @Insert
    suspend fun insertAll(features: List<SleepFeatureEntity>)

    @Query("SELECT * FROM sleep_features ORDER BY windowTimestamp ASC")
    suspend fun getAllFeatures(): List<SleepFeatureEntity>

    @Update
    suspend fun updateAll(features: List<SleepFeatureEntity>)

    @Query("DELETE FROM sleep_features")
    suspend fun clearAll()
}