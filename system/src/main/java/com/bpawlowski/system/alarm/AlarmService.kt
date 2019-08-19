package com.bpawlowski.system.alarm

import android.location.Location
import com.bpawlowski.database.entity.Contact

interface AlarmService {
    suspend fun raiseAlarm(contacts: List<Contact>?, location: Location)
}