package com.bpawlowski.system.accelometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.bpawlowski.system.model.AccelerometerEvent
import com.bpawlowski.system.util.accelerometer
import com.bpawlowski.system.util.sensorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive

class AccelerometerListener(context: Context) {

    private var listener: SensorEventListener? = null

    private val sensorManager: SensorManager by lazy {
        context.sensorManager
    }

    private val accelerometer: Sensor by lazy {
        sensorManager.accelerometer
    }

    @ExperimentalCoroutinesApi
    fun flow(): Flow<AccelerometerEvent> = callbackFlow {
        listener = getSensorListener(this, ::offer) { cancel("Error when acquiring events", it) }

        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        awaitClose { sensorManager.unregisterListener(listener) }
    }

    private fun getSensorListener(
        scope: CoroutineScope,
        emitter: (AccelerometerEvent) -> Boolean,
        onError: (Exception) -> Unit
    ) = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
            if (scope.isActive) {
                try {
                    if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                        val data = AccelerometerEvent(
                            event.timestamp,
                            event.values[0],
                            event.values[1],
                            event.values[2]
                        )
                        if (scope.isActive) {
                            emitter(data)
                        }
                    }
                } catch (e: Exception) {
                    onError(e)
                }
            }
        }
    }
}
