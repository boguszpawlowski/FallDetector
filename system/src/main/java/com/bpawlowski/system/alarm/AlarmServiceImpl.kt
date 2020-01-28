package com.bpawlowski.system.alarm

import com.bpawlowski.domain.model.Contact
import com.bpawlowski.domain.model.Location
import com.bpawlowski.domain.service.AlarmService
import com.bpawlowski.domain.service.ConnectivityService

internal class AlarmServiceImpl(
    private val connectivityService: ConnectivityService
) : AlarmService {

    override suspend fun raiseAlarm(contacts: List<Contact>, location: Location) {
        contacts
            .onEach { connectivityService.sendMessage(it.mobile, location) }
            .firstOrNull { it.isIce }?.let { iceContact ->
                connectivityService.call(iceContact)
            }
    }
}
