package com.bpawlowski.domain.service

import com.bpawlowski.domain.model.Location

interface TextMessageService {
    suspend fun sendMessage(number: Int, location: Location?)
}
