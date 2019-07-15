package bogusz.com.service.alarm

import android.content.Context
import android.location.Location
import bogusz.com.service.connectivity.CallService
import bogusz.com.service.connectivity.SmsService
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority

internal class AlarmServiceImpl(
	private val smsService: SmsService,
	private val callService: CallService,
	private val context: Context
) : AlarmService {

	override fun raiseAlarm(contacts: List<Contact>?, location: Location) {
		contacts
			?.onEach { smsService.sendMessage(it.mobile, location) }
			?.firstOrNull { it.priority == UserPriority.PRIORITY_ICE }
			?.let {
				callIce(it)
			}
	}

	private fun callIce(contact: Contact) = callService.call(context, contact)
}