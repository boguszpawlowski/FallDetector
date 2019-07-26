package bogusz.com.service.connectivity

import android.location.Location
import android.telephony.SmsManager

internal class TextMessageServiceImpl(
    private val smsManager: SmsManager
) : TextMessageService {

    override fun sendMessage(number: Int, location: Location) = with(smsManager) {
        val parts = divideMessage(location.smsBody)
//            sendMultipartTextMessage(number.toString(), null, parts, null, null) //todo
    }

    private val Location.smsBody: String
        get() = "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"
}