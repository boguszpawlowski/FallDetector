package bogusz.com.service.connectivity

import android.location.Location

interface ISmsService{
    fun sendMessage(number: Int, location: Location)
}