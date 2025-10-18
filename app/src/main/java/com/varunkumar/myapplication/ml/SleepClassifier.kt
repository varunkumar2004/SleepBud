package com.varunkumar.myapplication.ml

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SleepClassifier @Inject constructor(
    @ApplicationContext context: Context
) {
    private val model: SleepModel = SleepModel.newInstance(context)

    fun classify(motionVariance: Float): Int {
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 1), DataType.FLOAT32)
        inputFeature0.loadArray(floatArrayOf(motionVariance))

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val outputArray = outputFeature0.floatArray
        val maxIndex = outputArray.indices.maxByOrNull { outputArray[it] } ?: -1

        return maxIndex
    }
}