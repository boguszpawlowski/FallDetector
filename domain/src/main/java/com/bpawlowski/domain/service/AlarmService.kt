package com.bpawlowski.domain.service

import com.bpawlowski.domain.model.Contact
import com.bpawlowski.domain.model.Location

interface AlarmService {
    suspend fun raiseAlarm(contacts: List<Contact>?, location: Location)
}
