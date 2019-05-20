package com.example.bpawlowski.falldetector.monitoring

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import bogusz.com.service.accelometer.FallDetector
import bogusz.com.service.alarm.AlarmService
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.location.LocationProvider
import bogusz.com.service.model.Sensitivity
import bogusz.com.service.rx.SchedulerProvider
import com.example.bpawlowski.falldetector.FallDetectorApp
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.ui.main.MainActivity
import com.example.bpawlowski.falldetector.di.component.DaggerAppComponent
import com.example.bpawlowski.falldetector.monitoring.ServiceIntentType.*
import com.example.bpawlowski.falldetector.util.doNothing
import com.example.bpawlowski.falldetector.util.notificationManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

class BackgroundService : Service() {

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var alarmService: AlarmService

    @Inject
    lateinit var locationProvider: LocationProvider

    @Inject
    lateinit var contactRepository: ContactRepository

    private var sensitivity: Sensitivity = Sensitivity.HIGH

    private val fallDetector: FallDetector by lazy {
        FallDetector(applicationContext, schedulerProvider, sensitivity)
    }

    private val disposable = CompositeDisposable()

    override fun onBind(intent: Intent?) = null

    /**
     * The else branch is for situation when START_STICKY will restart service via intent with null action
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            START_SERVICE.name -> startService()
            STOP_SERVICE.name -> stopService()
            CHANGE_SENSITIVITY.name -> changeSensitivity(intent)
            else -> startService()
        }
        return START_STICKY
    }

    /**
     * Calling startForeground as soon as possible to avoid RemoteServiceException
     */
    override fun onCreate() {
        startForeground(ONGOING_NOTIFICATION_ID, buildNotification())
        super.onCreate()

        injectSelf()
        isServiceRunningSubject.onNext(true)
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable.dispose()
    }

    private fun stopService() {
        Timber.i("STOP")

        isServiceRunningSubject.onNext(false)
        stopForeground(true)
        stopSelf()
    }

    @SuppressLint("CheckResult")
    private fun startService() {
        Timber.i("START")

        disposable.add(
            fallDetector.getFallEvents()
                .subscribe(
                    { raiseAlarm(it) },
                    { Timber.e(it) },
                    { doNothing }
                )
        )
    } //TODO show AlarmActivity Screen

    private fun changeSensitivity(intent: Intent?) {
        TODO("implement changing sensitivity")
    }

    @Suppress("DEPRECATION")
    private fun buildNotification(): Notification {
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
            NotificationCompat.Builder(this, CHANNEL_ID)
        } else {
            NotificationCompat.Builder(this)
        }.setContentTitle(getText(R.string.notification_title))
            .setContentText(getText(R.string.notification_message))
            .setSmallIcon(R.drawable.icon_fall)
            .setContentIntent(pendingIntent)
            .setTicker(getText(R.string.ticker_text))
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_LOW
        with(NotificationChannel(CHANNEL_ID, name, importance)) {
            description = descriptionText
            notificationManager.createNotificationChannel(this)
        }
    }

    /**
     * If There is no movement, raise alarm, if there is still ask user if everything is ok
     */
    private fun raiseAlarm(shouldRaise: Boolean) {
        Timber.e("alarm: $shouldRaise")
        if (shouldRaise) {
            disposable.add(
                locationProvider.getLastKnownLocation()
                    .observeOn(schedulerProvider.IO)
                    .toSingle()
                    .zipWith(contactRepository.fetchAllContacts()) { location, list -> location to list }
                    .subscribe(
                        { pair -> alarmService.raiseAlarm(pair.second, pair.first) },
                        { Timber.e(it) }
                    )
            )
        }
    }

    private fun injectSelf() {
        DaggerAppComponent.builder()
            .application(application as FallDetectorApp)
            .build()
            .inject(this)
    }

    companion object {
        private const val CHANNEL_ID = "background_service_id"
        private const val SENSITIVITY_EXTRA = "sensiticity_extra"
        private const val ONGOING_NOTIFICATION_ID = 9999

        @JvmStatic
        fun startService(context: Context) {
            with(Intent(context, BackgroundService::class.java)) {
                action = START_SERVICE.name
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(this@with)
                } else {
                    context.startService(this@with)
                }
            }
        }

        @JvmStatic
        fun stopService(context: Context) {
            with(Intent(context, BackgroundService::class.java)) {
                action = STOP_SERVICE.name
                context.startService(this)
            }
        }

        @JvmStatic
        fun changeSensitivity(context: Context, sensitivity: Sensitivity) {
            with(Intent(context, BackgroundService::class.java)) {
                action = CHANGE_SENSITIVITY.name
                putExtra(SENSITIVITY_EXTRA, sensitivity)
                context.startService(this)
            }
        }

        @JvmStatic
        val isServiceRunningSubject = BehaviorSubject.createDefault(false)
    }
}
