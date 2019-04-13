package bogusz.com.service.alarm

import android.location.Location
import bogusz.com.service.model.Contact

interface IAlarmService {
    fun raiseAlarm(contacts: List<Contact>, location: Location)
}