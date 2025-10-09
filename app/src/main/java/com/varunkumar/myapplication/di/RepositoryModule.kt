package com.varunkumar.myapplication.di

import com.varunkumar.myapplication.data.datasource.AndroidSensorDataSource
import com.varunkumar.myapplication.data.datasource.SensorDataSource
import com.varunkumar.myapplication.data.repository.SleepRepository
import com.varunkumar.myapplication.data.repository.SleepRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSleepRepository(
        sleepRepositoryImpl: SleepRepositoryImpl
    ) : SleepRepository

    @Binds
    @Singleton
    abstract fun bindSensorDataSource(
        androidSensorDataSource: AndroidSensorDataSource
    ): SensorDataSource
}

