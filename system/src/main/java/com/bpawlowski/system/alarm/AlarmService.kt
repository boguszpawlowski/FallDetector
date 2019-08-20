package com.bpawlowski.system.alarm

import android.location.Location
import com.bpawlowski.core.model.Contact

interface AlarmService {
	suspend fun raiseAlarm(contacts: List<Contact>?, location: Location)
}