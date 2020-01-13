package com.bpawlowski.system.connectivity

import android.telephony.SmsManager
import com.bpawlowski.domain.model.Location
import com.bpawlowski.domain.service.TextMessageService

internal class TextMessageServiceImpl(
    private val smsManager: SmsManager
) : TextMessageService {

    override suspend fun sendMessage(number: Int, location: Location?) = with(smsManager) {

        val message = location?.smsBody ?: smsWithoutLocation
        val parts = divideMessage(message)
        // 			sendMultipartTextMessage(number.toString(), null, parts, null, null) //todo uncomment + sms without location text
    }

    private val smsWithoutLocation = "l"

    private val Location.smsBody: String
        get() = "https://www.google.com/maps/search/?api=1&query=$lat,$lng"
}
