package com.bpawlowski.system.connectivity

import android.location.Location

interface TextMessageService{
    suspend fun sendMessage(number: Int, location: Location)
}