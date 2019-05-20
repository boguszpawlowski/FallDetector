package com.example.bpawlowski.falldetector.ui.alarm

import bogusz.com.service.alarm.AlarmService
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.location.LocationProvider
import bogusz.com.service.rx.SchedulerProvider
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import io.reactivex.rxkotlin.zipWith
import timber.log.Timber
import javax.inject.Inject

class AlarmViewModel @Inject constructor(
    private val locationsProvider: LocationProvider,
    private val schedulerProvider: SchedulerProvider,
    private val contactRepository: ContactRepository,
    private val alarmService: AlarmService
) : BaseViewModel() {

    fun raiseAlarm() {
        disposable.add(
            locationsProvider.getLastKnownLocation()
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