package bogusz.com.service.connectivity

import android.location.Location
import android.telephony.SmsManager
import javax.inject.Inject

class SmsService @Inject constructor() : ISmsService {

    override fun sendMessage(number: Int, location: Location) {
        val smsManager = SmsManager.getDefault()
        with(smsManager) {
            val parts = divideMessage(location.smsBody)
            sendMultipartTextMessage(number.toString(), null, parts, null, null)
        }
    }


    val Location.smsBody: String
        get() = "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"

    companion object {
        private val TAG = SmsService::class.java.name
    }
}