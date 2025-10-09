package com.varunkumar.myapplication.data.datasource

import com.varunkumar.myapplication.data.local.SleepSessionDao
import com.varunkumar.myapplication.data.local.SleepSessionEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val sleepSessionDao: SleepSessionDao
) {
    suspend fun insertSleepSession(sleepSessionEntity: SleepSessionEntity) {
        sleepSessionDao.insert(sleepSessionEntity)
    }
}