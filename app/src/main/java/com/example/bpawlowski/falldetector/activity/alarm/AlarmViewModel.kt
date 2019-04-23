package com.example.bpawlowski.falldetector.activity.alarm

import android.util.Log
import bogusz.com.service.alarm.IAlarmService
import bogusz.com.service.database.repository.IContactRepository
import bogusz.com.service.location.ILocationProvider
import bogusz.com.service.rx.ISchedulerProvider
import com.example.bpawlowski.falldetector.activity.base.activity.BaseViewModel
import io.reactivex.rxkotlin.zipWith
import javax.inject.Inject

class AlarmViewModel @Inject constructor(
    private val locationsProvider: ILocationProvider,
    private val schedulerProvider: ISchedulerProvider,
    private val contactRepository: IContactRepository,
    private val alarmService: IAlarmService
) : BaseViewModel() {

    fun raiseAlarm() {
        disposable.add(
            locationsProvider.getLastKnownLocation()
                .observeOn(schedulerProvider.IO)
                .toFlowable()
                .zipWith(contactRepository.getAllContacts()) { location, list -> location to list }
                .subscribe(
                    { pair -> alarmService.raiseAlarm(pair.second, pair.first) },
                    { Log.e(TAG, it.message, it) }
                )
        )
    }

    companion object {
        private val TAG = AlarmViewModel::class.java.simpleName
    }
}