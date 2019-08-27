@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.bpawlowski.system.accelometer

import android.content.Context
import com.bpawlowski.core.model.Sensitivity
import com.bpawlowski.system.model.AccelerometerEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.take
import timber.log.Timber
import kotlin.math.abs

class FallDetector(
	private val context: Context,
	private var sensitivity: Sensitivity = Sensitivity.HIGH
) {

	private val accelerometerListener by lazy {
		AccelerometerListener(context)
	}

	fun getFallEvents(): Flow<Boolean> =
		accelerometerListener.flow()
			.skipUntilPeak(sensitivity)
			.detectNoMovement(sensitivity)
			.flowOn(Dispatchers.Default)

	fun changeSensitivity(sensitivity: Sensitivity) {
		this.sensitivity = sensitivity
	}
}

private fun Flow<AccelerometerEvent>.skipUntilPeak(sensitivity: Sensitivity): Flow<AccelerometerEvent> =
	dropWhile { event ->
		Timber.e("before dropping: $event")
		event.mean < sensitivity.maxAcc
	}

/**
 * Accumulates items for period defined in [Sensitivity], then compares its mean and emits result.
 * After one emission whole stream is completed and subscriber then resubscribes.
 * Throwing error to restart the flow after false alarm
 */
private fun Flow<AccelerometerEvent>.detectNoMovement(sensitivity: Sensitivity): Flow<Boolean> =
	drop(30) //todo number count
		.map { event -> event.mean }
		.take(100)
		.scan(mutableListOf()) { accumulator: MutableList<Float>, value -> accumulator.add(value); accumulator }
		.drop(100)
		.map { buffer ->
			if (abs(buffer.toTypedArray().average()) < sensitivity.meanAccDuringNoMovement) {
				true
			} else throw IllegalStateException()
		}.retryWhen { cause, _ -> cause is IllegalStateException }