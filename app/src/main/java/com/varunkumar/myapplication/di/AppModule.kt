package com.varunkumar.myapplication.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE sleep_session_data ADD COLUMN audioAmplitude INTEGER NOT NULL DEFAULT 0")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE sleep_session_data ADD COLUMN gyroX REAL NOT NULL DEFAULT 0.0")
                db.execSQL("ALTER TABLE sleep_session_data ADD COLUMN gyroY REAL NOT NULL DEFAULT 0.0")
                db.execSQL("ALTER TABLE sleep_session_data ADD COLUMN gyroZ REAL NOT NULL DEFAULT 0.0")
            }
        }

        return Room.databaseBuilder(
            context.applicationContext,
            SleepDatabase::class.java,
            "sleep_database"
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
    }

    @Provides
    @Singleton
    fun provideSleepSessionDao(
        database : SleepDatabase
    ) : SleepSessionDao {
        return database.sleepSessionDao()
    }
}