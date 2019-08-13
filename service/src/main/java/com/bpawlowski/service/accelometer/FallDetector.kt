package com.bpawlowski.service.accelometer

import android.content.Context
import com.bpawlowski.service.model.AccelerometerEvent
import com.bpawlowski.service.model.Sensitivity
import com.bpawlowski.service.rx.SchedulerProvider
import io.reactivex.Flowable
import java.lang.Math.abs
import java.util.concurrent.TimeUnit

class FallDetector(
    private val context: Context,
    private val schedulerProvider: SchedulerProvider,
    private var sensitivity: Sensitivity = Sensitivity.HIGH
) {

    private val accelerometerFlowable by lazy {
        AccelerometerFlowable(context)
    }

    fun getFallEvents(): Flowable<Boolean> =
        accelerometerFlowable
            .subscribeOn(schedulerProvider.COMPUTATION)
            .skipUntilPeak(sensitivity)
            .detectNoMovement(sensitivity)

    fun changeSensitivity(sensitivity: Sensitivity) {
        this.sensitivity = sensitivity
    }
}

private fun Flowable<AccelerometerEvent>.skipUntilPeak(sensitivity: Sensitivity): Flowable<AccelerometerEvent> =
    skipWhile { event ->
        event.mean < sensitivity.maxAcc
    }

/**
 * Accumulates items for period defined in [Sensitivity], then compares its mean and emits result.
 * After one emission whole stream is completed and subscriber then resubscribes.
 */
private fun Flowable<AccelerometerEvent>.detectNoMovement(sensitivity: Sensitivity): Flowable<Boolean> =
    skip(2, TimeUnit.SECONDS)
        .map { event -> event.mean }
        .buffer(sensitivity.noMovementPeriod, TimeUnit.SECONDS)
        .map { buffer ->
            abs(buffer.toTypedArray().average()) < sensitivity.meanAccDuringNoMovement
        }
        .take(1)
        .repeat()