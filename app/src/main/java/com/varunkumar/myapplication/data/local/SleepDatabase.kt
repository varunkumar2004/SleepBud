package com.varunkumar.myapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SleepSessionEntity::class],
    version = 3
)
abstract class SleepDatabase: RoomDatabase() {
    abstract fun sleepSessionDao(): SleepSessionDao
}