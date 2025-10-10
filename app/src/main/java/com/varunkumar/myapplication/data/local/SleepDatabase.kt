package com.varunkumar.myapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.varunkumar.myapplication.data.local.dao.SleepFeatureDao
import com.varunkumar.myapplication.data.local.dao.SleepSessionDao
import com.varunkumar.myapplication.data.local.entity.SleepFeatureEntity
import com.varunkumar.myapplication.data.local.entity.SleepSessionEntity

@Database(
    entities = [
        SleepSessionEntity::class,
        SleepFeatureEntity::class
   ],
    version = 4
)
abstract class SleepDatabase: RoomDatabase() {
    abstract fun sleepSessionDao(): SleepSessionDao
    abstract fun sleepFeatureDao(): SleepFeatureDao
}