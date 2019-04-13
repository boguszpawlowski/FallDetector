package bogusz.com.service.alarm

import android.content.Context
import android.location.Location
import bogusz.com.service.connectivity.CallService
import bogusz.com.service.connectivity.ISmsService
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import javax.inject.Inject

class AlarmService @Inject constructor(
    private val smsService: ISmsService,
    private val callService: CallService,
    private val context: Context
) : IAlarmService {

    override fun raiseAlarm(contacts: List<Contact>, location: Location) { //TODO check if it always works
        contacts
            .onEach { smsService.sendMessage(it.mobile, location) }
            .firstOrNull { it.priority == UserPriority.PRIORITY_ICE }
            ?.let {
                callIce(it)
            }
    }

    private fun callIce(contact: Contact) = callService.call(context, contact)
}