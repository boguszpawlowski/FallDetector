package com.example.bpawlowski.falldetector.monitoring

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import bogusz.com.service.accelometer.AccelerometerFlowable
import bogusz.com.service.rx.SchedulerProvider
import com.example.bpawlowski.falldetector.FallDetectorApp
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.activity.main.MainActivity
import com.example.bpawlowski.falldetector.di.component.DaggerAppComponent
import com.example.bpawlowski.falldetector.monitoring.ServiceIntentType.START_SERVICE
import com.example.bpawlowski.falldetector.monitoring.ServiceIntentType.STOP_SERVICE
import com.example.bpawlowski.falldetector.util.notificationManager
import com.example.bpawlowski.falldetector.util.toast
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

class BackgroundService : Service() {

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    private val accelerometerFlowable: AccelerometerFlowable by lazy {
        AccelerometerFlowable(applicationContext)
    }

    override fun onBind(intent: Intent?) = null

    /**
     * The else branch is for situation when START_STICKY will restart service via intent with null action
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            START_SERVICE.name -> startService()
            STOP_SERVICE.name -> stopService()
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

        accelerometerFlowable.dispose()
    }

    private fun stopService() {
        isServiceRunningSubject.onNext(false)
        stopForeground(true)
        stopSelf()
    }

    @SuppressLint("CheckResult")
    private fun startService() {
        toast("Started background service")

        accelerometerFlowable
            .subscribeOn(schedulerProvider.COMPUTATION)
            .subscribe { Timber.e("working: ${it.timestamp} ${it.x} ") }
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

    private fun injectSelf() {
        DaggerAppComponent.builder()
            .application(application as FallDetectorApp)
            .build()
            .inject(this)
    }

    companion object {
        private const val CHANNEL_ID = "background_service_id"
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
        val isServiceRunningSubject = BehaviorSubject.createDefault(false)
    }
}
