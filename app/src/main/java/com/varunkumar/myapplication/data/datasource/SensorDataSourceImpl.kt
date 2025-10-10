package com.varunkumar.myapplication.data.datasource

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaRecorder
import android.util.Log
import com.varunkumar.myapplication.data.model.RawSensorData
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.Timer

@Singleton
class AndroidSensorDataSource @Inject constructor(
    @ApplicationContext private val context: Context
): SensorDataSource {
    private val sensorManager= context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val gyrometer = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    private var latestAccel = floatArrayOf(0f, 0f, 0f)
    private var latestGyro = floatArrayOf(0f, 0f, 0f)

    override fun startTracking(): Flow<RawSensorData> = callbackFlow {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    when(it.sensor.type) {
                        Sensor.TYPE_ACCELEROMETER -> {
                            latestAccel = it.values.clone()

                            val rawData = RawSensorData(
                                timestamp = System.currentTimeMillis(),
                                accX = it.values[0],
                                accY = it.values[1],
                                accZ = it.values[2],
                                gyroX = latestGyro[0],
                                gyroY = latestGyro[1],
                                gyroZ = latestGyro[2],
                                audioAmplitude = 0
                            )

                            trySend(rawData)
                        }

                        Sensor.TYPE_GYROSCOPE -> {
                            latestGyro = it.values.clone()
                        }
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, value: Int) {
                Log.d("AndroidSensorDataSource; sensor: $sensor", "onAccuracyChanged: $value")
            }
        }

        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(listener, gyrometer, SensorManager.SENSOR_DELAY_NORMAL)

        awaitClose {
            sensorManager.unregisterListener(listener)
        }
    }
}