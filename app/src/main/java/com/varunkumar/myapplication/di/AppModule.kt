package com.varunkumar.myapplication.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.varunkumar.myapplication.data.local.SleepDatabase
import com.varunkumar.myapplication.data.local.dao.SleepFeatureDao
import com.varunkumar.myapplication.data.local.dao.SleepSessionDao
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

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // SQL command to create the new table for processed features
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `sleep_features` (" +
                            "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "`windowTimestamp` INTEGER NOT NULL, " +
                            "`motionVariance` REAL NOT NULL, " +
                            "`rotationVariance` REAL NOT NULL, " +
                            "`averageAmplitude` REAL NOT NULL, " +
                            "`peakAmplitude` INTEGER NOT NULL, " +
                            "`predictedStage` TEXT)"
                )
            }
        }

        return Room.databaseBuilder(
            context.applicationContext,
            SleepDatabase::class.java,
            "sleep_database"
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4).build()
    }

    @Provides
    @Singleton
    fun provideSleepSessionDao(
        database : SleepDatabase
    ) : SleepSessionDao {
        return database.sleepSessionDao()
    }

    @Provides
    @Singleton
    fun provideSleepFeatureDao(
        database : SleepDatabase
    ) : SleepFeatureDao {
        return database.sleepFeatureDao()
    }
}