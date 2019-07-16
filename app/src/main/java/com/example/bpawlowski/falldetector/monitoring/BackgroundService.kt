package com.example.bpawlowski.falldetector.monitoring

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import bogusz.com.service.accelometer.FallDetector
import bogusz.com.service.alarm.AlarmService
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.database.repository.ServiceStateRepository
import bogusz.com.service.database.zip
import bogusz.com.service.location.LocationProvider
import bogusz.com.service.model.Sensitivity
import bogusz.com.service.rx.SchedulerProvider
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.monitoring.ServiceIntentType.START_SERVICE
import com.example.bpawlowski.falldetector.monitoring.ServiceIntentType.STOP_SERVICE
import com.example.bpawlowski.falldetector.ui.main.MainActivity
import com.example.bpawlowski.falldetector.util.doNothing
import com.example.bpawlowski.falldetector.util.notificationManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class BackgroundService : Service(), CoroutineScope {

	private val schedulerProvider: SchedulerProvider by inject()
	private val alarmService: AlarmService by inject()
	private val locationProvider: LocationProvider by inject()
	private val contactRepository: ContactRepository by inject()
	private val serviceStateRepository: ServiceStateRepository by inject()

	override val coroutineContext = Dispatchers.Main + SupervisorJob()

	private val disposable = CompositeDisposable()

	private val fallDetector: FallDetector by lazy {
		FallDetector(applicationContext, schedulerProvider)
	}

	private val sensitivityData by lazy {
		serviceStateRepository.getSensitivityData()
	}

	private val sensitivityObserver by lazy {
		Observer<Sensitivity> { sensitivity ->
			sensitivity?.let {
				fallDetector.changeSensitivity(it)
			}
		}
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

		launch {
			serviceStateRepository.initiateState()
		}
		sensitivityData.observeForever(sensitivityObserver)
	}

	override fun onDestroy() {
		super.onDestroy()

		sensitivityData.removeObserver(sensitivityObserver)
		disposable.dispose()
	}

	private fun stopService() {
		Timber.i("STOP")

		launch {
			serviceStateRepository.updateIsRunning(false)
			stopForeground(true)
			stopSelf()
		}
	}

	private fun startService() {
		Timber.i("START")

		launch {
			serviceStateRepository.updateIsRunning(true)
		}
		disposable.add(
			fallDetector.getFallEvents().subscribe(
				{ raiseAlarm(it) },
				{ Timber.e(it) },
				{ doNothing }
			)
		)
	} //TODO show AlarmActivity Screen

	@Suppress("DEPRECATION")
	private fun buildNotification(): Notification {
		val pendingIntent = Intent(this, MainActivity::class.java).apply {
			action = Intent.ACTION_MAIN
			addCategory(Intent.CATEGORY_LAUNCHER)
		}.let { notificationIntent ->
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
	private fun raiseAlarm(shouldRaise: Boolean) = launch {
		if (shouldRaise) {
			val contacts = async { contactRepository.getAllContacts() }
			val location = async { locationProvider.getLastKnownLocation() }

			zip(contacts.await(), location.await()) { first, second ->
				first to second
			}.onSuccess { alarmService.raiseAlarm(it.first, it.second) }
				.onFailure { Timber.e(it) }
		}
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
	}
}