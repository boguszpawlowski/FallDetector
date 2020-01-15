package com.bpawlowski.domain.service

import com.bpawlowski.domain.model.Contact
import com.bpawlowski.domain.model.Location

interface ConnectivityService {
    fun sendMessage(number: Int, location: Location?)
    fun call(contact: Contact)
}
