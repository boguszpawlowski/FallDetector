package bogusz.com.service.accelometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import bogusz.com.service.model.AccelerometerEvent
import bogusz.com.service.util.accelerometer
import bogusz.com.service.util.sensorManager
import javax.inject.Inject

class AccelerometerLiveData(
    context: Context
) : LiveData<AccelerometerEvent>(), SensorEventListener { //TODO replace LiveData with Subject, or with Relay

    private val sensorManager: SensorManager by lazy {
        context.sensorManager
    }

    private val accelerometer: Sensor by lazy {
        sensorManager.accelerometer
    }

    override fun onActive() {
        super.onActive()

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onInactive() {
        super.onInactive()

        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {

            val data = AccelerometerEvent(event.timestamp, event.values[0], event.values[1], event.values[2])
            postValue(data)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
