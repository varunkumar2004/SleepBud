package com.varunkumar.myapplication.utils

import kotlin.math.pow

fun calculateVariance(data: List<Float>): Float {
    if (data.size < 2) return 0f
    val mean = data.average().toFloat()
    return data.map { (it - mean).pow(2) }.sum() / (data.size - 1)
}