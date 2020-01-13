package com.bpawlowski.system.util

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager

val Context.sensorManager: SensorManager
    get() = getSystemService(Context.SENSOR_SERVICE) as SensorManager

val SensorManager.accelerometer: Sensor
    get() = getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

val Context.notificationManager: NotificationManager
    get() = getSystemService(IntentService.NOTIFICATION_SERVICE) as NotificationManager
