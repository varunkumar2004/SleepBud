package com.varunkumar.myapplication.di

import android.content.Context
import androidx.room.Room
import com.varunkumar.myapplication.data.local.SleepDatabase
import com.varunkumar.myapplication.data.local.SleepSessionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSleepDatabase(
        @ApplicationContext context: Context
    ) : SleepDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            SleepDatabase::class.java,
            "sleep_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSleepSessionDao(
        database : SleepDatabase
    ) : SleepSessionDao {
        return database.sleepSessionDao()
    }
}