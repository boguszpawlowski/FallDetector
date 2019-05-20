package bogusz.com.service.accelometer

import android.content.Context
import bogusz.com.service.model.AccelerometerEvent
import bogusz.com.service.model.Sensitivity
import bogusz.com.service.rx.SchedulerProvider
import io.reactivex.Flowable
import timber.log.Timber
import java.lang.Math.abs
import java.util.concurrent.TimeUnit
import javax.inject.Inject
class FallDetector (
    private val context: Context,
    private val schedulerProvider: SchedulerProvider,
    private var sensitivity: Sensitivity
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
        Timber.d("timestamp: ${event.timestamp}, mean: ${event.mean}")
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
        .doOnNext { Timber.i("no movement $it") }
        .take(1)
        .doOnComplete { Timber.i("completed") }
        .repeat()