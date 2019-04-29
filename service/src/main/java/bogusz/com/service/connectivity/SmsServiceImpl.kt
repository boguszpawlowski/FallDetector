package bogusz.com.service.connectivity

import android.location.Location
import android.telephony.SmsManager
import javax.inject.Inject

internal class SmsServiceImpl @Inject constructor() : SmsService {

    override fun sendMessage(number: Int, location: Location) {
        val smsManager = SmsManager.getDefault()
        with(smsManager) {
            val parts = divideMessage(location.smsBody)
            sendMultipartTextMessage(number.toString(), null, parts, null, null)
        }
    }

    private val Location.smsBody: String
        get() = "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"
}