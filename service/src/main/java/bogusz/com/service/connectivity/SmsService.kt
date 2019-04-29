package bogusz.com.service.connectivity

import android.location.Location

interface SmsService{
    fun sendMessage(number: Int, location: Location)
}