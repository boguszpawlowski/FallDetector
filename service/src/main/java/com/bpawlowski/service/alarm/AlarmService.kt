package com.bpawlowski.service.alarm

import android.location.Location
import com.bpawlowski.service.model.Contact

interface AlarmService {
    suspend fun raiseAlarm(contacts: List<Contact>?, location: Location)
}