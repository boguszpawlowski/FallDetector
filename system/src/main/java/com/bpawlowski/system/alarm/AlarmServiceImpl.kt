package com.bpawlowski.system.alarm

import android.content.Context
import android.location.Location
import com.bpawlowski.core.model.Contact
import com.bpawlowski.core.model.ContactPriority
import com.bpawlowski.system.connectivity.CallService
import com.bpawlowski.system.connectivity.TextMessageService

internal class AlarmServiceImpl(
	private val textMessageService: TextMessageService,
	private val callService: CallService,
	private val context: Context
) : AlarmService {

	override suspend fun raiseAlarm(contacts: List<Contact>?, location: Location) {
		contacts
			?.onEach { textMessageService.sendMessage(it.mobile, location) }
			?.firstOrNull { it.priority == ContactPriority.PRIORITY_ICE }
			?.let {
				callIce(it)
			}
	}

	private fun callIce(contact: Contact) = callService.call(context, contact)
}