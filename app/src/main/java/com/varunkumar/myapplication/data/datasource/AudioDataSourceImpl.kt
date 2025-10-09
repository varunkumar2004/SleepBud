package com.varunkumar.myapplication.data.datasource

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.File
import kotlin.concurrent.timer

class AndroidAudioDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : AudioDataSource {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun startTracking(): Flow<Int> = callbackFlow {
        val mediaRecorder: MediaRecorder? = MediaRecorder(context)

        val dummyFile = File(context.cacheDir, "audio_temp.3gp")

        mediaRecorder?.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(dummyFile.absolutePath)

            try {
                prepare()
                start()
            } catch (e: Exception) {
                close(e)
            }
        }

        val timer = timer(period = 500L) {
            trySend(mediaRecorder?.maxAmplitude ?: 0)
        }

        awaitClose {
            timer.cancel()
            mediaRecorder?.apply {
                try {
                    stop()
                    release()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}