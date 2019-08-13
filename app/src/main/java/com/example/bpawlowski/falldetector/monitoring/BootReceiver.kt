package com.example.bpawlowski.falldetector.monitoring

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bpawlowski.service.database.repository.ServiceStateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class BootReceiver : BroadcastReceiver(), KoinComponent, CoroutineScope {

	private val job = SupervisorJob()

	override val coroutineContext = Dispatchers.Main + job

	private val serviceStateRepository by inject<ServiceStateRepository>()

	@SuppressLint("UnsafeProtectedBroadcastReceiver")
	override fun onReceive(context: Context?, intent: Intent?) {
		launch(Dispatchers.IO) {
			context?.let {
				serviceStateRepository.getIsRunningFlag().onSuccess {
					if (it) {
						BackgroundService.startService(context)
					}
				}
			}
		}
	}
}