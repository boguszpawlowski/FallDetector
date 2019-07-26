package bogusz.com.service.connectivity

import android.location.Location

interface TextMessageService{
    fun sendMessage(number: Int, location: Location)
}