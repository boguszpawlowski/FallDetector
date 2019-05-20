package bogusz.com.service.accelometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import bogusz.com.service.model.AccelerometerEvent
import bogusz.com.service.util.accelerometer
import bogusz.com.service.util.sensorManager
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscriber

class AccelerometerFlowable(
    context: Context
) : Flowable<AccelerometerEvent>(), SensorEventListener, Disposable {

    private var accelerometerSubscriber: Subscriber<in AccelerometerEvent>? = null

    private val sensorManager: SensorManager by lazy {
        context.sensorManager
    }

    private val accelerometer: Sensor by lazy {
        sensorManager.accelerometer
    }

    override fun subscribeActual(subscriber: Subscriber<in AccelerometerEvent>?) {
        subscriber?.let {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
            accelerometerSubscriber = it
        }
    }

    override fun dispose() {
        if (isDisposed.not()) {
            accelerometerSubscriber = null
            sensorManager.unregisterListener(this, accelerometer)
        }
    }

    override fun isDisposed() = accelerometerSubscriber == null

    override fun onSensorChanged(event: SensorEvent?) {
        try {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {

                val data = AccelerometerEvent(event.timestamp, event.values[0], event.values[1], event.values[2])
                accelerometerSubscriber?.onNext(data)
            }
        } catch (e: Exception) {
            accelerometerSubscriber?.onError(e)
            dispose()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
