package com.varunkumar.myapplication.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
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
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(connection: SQLiteConnection) {
                connection.execSQL("ALTER TABLE sleep_session_data ADD COLUMN audioAmplitude INTEGER NOT NULL DEFAULT 0")
            }
        }

        return Room.databaseBuilder(
            context.applicationContext,
            SleepDatabase::class.java,
            "sleep_database"
        ).addMigrations(MIGRATION_1_2).build()
    }

    @Provides
    @Singleton
    fun provideSleepSessionDao(
        database : SleepDatabase
    ) : SleepSessionDao {
        return database.sleepSessionDao()
    }
}