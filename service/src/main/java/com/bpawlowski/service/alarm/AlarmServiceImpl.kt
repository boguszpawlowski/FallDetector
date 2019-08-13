package com.bpawlowski.service.alarm

import android.content.Context
import android.location.Location
import com.bpawlowski.service.connectivity.CallService
import com.bpawlowski.service.connectivity.TextMessageService
import com.bpawlowski.service.model.Contact
import com.bpawlowski.service.model.UserPriority

internal class AlarmServiceImpl(
	private val textMessageService: TextMessageService,
	private val callService: CallService,
	private val context: Context
) : AlarmService {

	override suspend fun raiseAlarm(contacts: List<Contact>?, location: Location) {
		contacts
			?.onEach { textMessageService.sendMessage(it.mobile, location) }
			?.firstOrNull { it.priority == UserPriority.PRIORITY_ICE }
			?.let {
				callIce(it)
			}
	}

	private fun callIce(contact: Contact) = callService.call(context, contact)
}