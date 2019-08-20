package com.bpawlowski.system.accelometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.bpawlowski.core.util.doNothing
import com.bpawlowski.system.model.AccelerometerEvent
import com.bpawlowski.system.util.accelerometer
import com.bpawlowski.system.util.sensorManager
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableSubscriber
import io.reactivex.exceptions.MissingBackpressureException
import io.reactivex.internal.subscriptions.SubscriptionHelper
import io.reactivex.internal.util.BackpressureHelper
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference

class AccelerometerFlowable(
	context: Context,
	private val backpressureStrategy: BackpressureStrategy = BackpressureStrategy.DROP
) : Flowable<AccelerometerEvent>(), SensorEventListener {

	private val subscribers = ConcurrentHashMap<Int, Subscriber<in AccelerometerEvent>>()

	private val sensorManager: SensorManager by lazy {
		context.sensorManager
	}

	private val accelerometer: Sensor by lazy {
		sensorManager.accelerometer
	}

	override fun subscribeActual(s: Subscriber<in AccelerometerEvent>?) {
		s?.let {
			val subscriber = AccelerometerFlowableSubscriber(it, backpressureStrategy, this)
			if (subscribers.isEmpty()) {
				sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
			}
			subscribers[subscriber.hashCode()] = subscriber
			subscriber.onSubscribe(subscriber)
		}
	}

	private fun removeSubscriber(subscriber: AccelerometerFlowableSubscriber) {
		subscribers.remove(subscriber.hashCode())
		if (subscribers.isEmpty()) {
			sensorManager.unregisterListener(this, accelerometer)
		}
	}

	private fun clear() {
		subscribers.clear()
		sensorManager.unregisterListener(this, accelerometer)
	}

	override fun onSensorChanged(event: SensorEvent?) {
		try {
			if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
				val data = AccelerometerEvent(
					event.timestamp,
					event.values[0],
					event.values[1],
					event.values[2]
				)
				subscribers.forEach { _, subscriber -> subscriber.onNext(data) }
			}
		} catch (e: Exception) {
			subscribers.forEach { _, subscriber -> subscriber.onError(e) }
			clear()
		}
	}

	override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
	}

	private inner class AccelerometerFlowableSubscriber(
		private val downstream: Subscriber<in AccelerometerEvent>,
		private val backpressureStrategy: BackpressureStrategy,
		private var other: AccelerometerFlowable
	) : AtomicReference<Subscription>(), FlowableSubscriber<AccelerometerEvent>, Subscription {

		private val requested: AtomicLong = AtomicLong()

		override fun onNext(t: AccelerometerEvent) {
			if (requested.get() != 0L) {
				BackpressureHelper.produced(requested, 1)
				downstream.onNext(t)
			} else {
				onOverflow()
			}
		}

		override fun onError(t: Throwable) {
			downstream.onError(t)
		}

		override fun onComplete() {
			downstream.onComplete()
		}

		override fun onSubscribe(s: Subscription) {
			downstream.onSubscribe(s)
		}

		override fun request(n: Long) {
			SubscriptionHelper.deferredRequest(this, requested, n)
		}

		override fun cancel() {
			other.removeSubscriber(this)
			SubscriptionHelper.cancel(this)
		}

		private fun onOverflow() {
			when (backpressureStrategy) {
				BackpressureStrategy.MISSING -> doNothing
				BackpressureStrategy.ERROR -> throw MissingBackpressureException("create: could not emit value due to lack of requests")
				BackpressureStrategy.DROP -> doNothing
				else -> throw RuntimeException("Backpressure strategy not supported")
			}
		}
	}
}