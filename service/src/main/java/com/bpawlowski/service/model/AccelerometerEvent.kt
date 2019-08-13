package com.bpawlowski.service.model

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class AccelerometerEvent(
    val timestamp: Long,
    val x: Float,
    val y: Float,
    val z: Float
) {
    val mean: Float
        get() = abs(sqrt(x.pow(2) + y.pow(2) + z.pow(2)) - 9.81F)
}