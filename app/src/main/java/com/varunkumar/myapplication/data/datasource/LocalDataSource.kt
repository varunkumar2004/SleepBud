package com.varunkumar.myapplication.data.datasource

import com.varunkumar.myapplication.data.local.dao.SleepFeatureDao
import com.varunkumar.myapplication.data.local.dao.SleepSessionDao
import com.varunkumar.myapplication.data.local.entity.SleepFeatureEntity
import com.varunkumar.myapplication.data.local.entity.SleepSessionEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val sleepSessionDao: SleepSessionDao,
    private val sleepFeatureDao: SleepFeatureDao
) {
    suspend fun insertSleepData(sleepSessionEntity: SleepSessionEntity) {
        sleepSessionDao.insert(sleepSessionEntity)
    }

    suspend fun getAllRawSleepData(): List<SleepSessionEntity> {
        return sleepSessionDao.getAll()
    }

    suspend fun clearAllRawSleepData() {
        sleepSessionDao.clearAll()
    }

    suspend fun insertSleepFeatures(features: List<SleepFeatureEntity>) {
        sleepFeatureDao.insertAll(features)
    }

    suspend fun clearAllFeatures() {
        sleepFeatureDao.clearAll()
    }
}