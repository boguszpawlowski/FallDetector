package com.bpawlowski.domain.service

import com.bpawlowski.domain.model.Contact

interface CallService {
    fun call(contact: Contact)
}
