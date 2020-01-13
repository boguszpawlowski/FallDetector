package com.bpawlowski.system.alarm

import com.bpawlowski.domain.model.Contact
import com.bpawlowski.domain.model.ContactPriority
import com.bpawlowski.domain.model.Location
import com.bpawlowski.domain.service.AlarmService
import com.bpawlowski.domain.service.CallService
import com.bpawlowski.domain.service.TextMessageService

internal class AlarmServiceImpl(
    private val textMessageService: TextMessageService,
    private val callService: CallService
) : AlarmService {

    override suspend fun raiseAlarm(contacts: List<Contact>?, location: Location) {
        contacts
            ?.onEach { textMessageService.sendMessage(it.mobile, location) }
            ?.firstOrNull { it.priority == ContactPriority.PRIORITY_ICE }
            ?.let {
                callIce(it)
            }
    }

    private fun callIce(contact: Contact) = callService.call(contact)
}
