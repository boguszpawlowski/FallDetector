package com.bpawlowski.system.util

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager

val Context.sensorManager: SensorManager
    get() = getSystemService(Context.SENSOR_SERVICE) as SensorManager

val SensorManager.accelerometer: Sensor
    get() = getDefaultSensor(Sensor.TYPE_ACCELEROMETER)