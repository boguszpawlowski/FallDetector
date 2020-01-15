package com.bpawlowski.system.connectivity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.SmsManager
import com.bpawlowski.domain.model.Contact
import com.bpawlowski.domain.model.Location
import com.bpawlowski.domain.service.ConnectivityService

internal class ConnectivityServiceImpl(
    private val smsManager: SmsManager,
    private val context: Context
) : ConnectivityService {

    override fun sendMessage(number: Int, location: Location?) = with(smsManager) {
        val message = location?.smsBody ?: smsWithoutLocation
        val parts = divideMessage(message)
        // 			sendMultipartTextMessage(number.toString(), null, parts, null, null) //todo uncomment + sms without location text
    }

    override fun call(contact: Contact) = with(Intent(Intent.ACTION_CALL)) {
        data = Uri.parse("tel:${contact.mobile}")
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(this)
    }

    private val smsWithoutLocation = "l"

    private val Location.smsBody: String
        get() = "https://www.google.com/maps/search/?api=1&query=$lat,$lng"
}
