package com.example.bpawlowski.falldetector.ui.main.home

import android.content.Context
import com.bpawlowski.service.database.exceptions.FallDetectorException
import com.bpawlowski.service.database.repository.ServiceStateRepository
import com.example.bpawlowski.falldetector.monitoring.BackgroundService
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val serviceStateRepository: ServiceStateRepository
) : BaseViewModel() {

    fun toggleService(context: Context) = backgroundScope.launch {
        serviceStateRepository.getIsRunningFlag().fold(
            onSuccess = { isRunning ->
                if (isRunning) {
                    BackgroundService.stopService(context)
                } else {
                    BackgroundService.startService(context)
                }
            },
            onFailure = {
                if (it is FallDetectorException.NoRecordsException) {
                    BackgroundService.startService(context)
                } else {
                    Timber.e(it)
                }
            }
        )
    }
}