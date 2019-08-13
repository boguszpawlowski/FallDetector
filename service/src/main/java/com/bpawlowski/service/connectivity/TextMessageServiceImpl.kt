package com.bpawlowski.service.connectivity

import android.location.Location
import android.telephony.SmsManager
import com.bpawlowski.service.database.repository.ServiceStateRepository

internal class TextMessageServiceImpl(
    private val smsManager: SmsManager,
	private val serviceStateRepository: ServiceStateRepository
) : TextMessageService {

    override suspend fun sendMessage(number: Int, location: Location) = with(smsManager) {
		val serviceState = serviceStateRepository.getServiceState().getOrThrow()

		if(serviceState.sendingSms){
			val parts = divideMessage(if(serviceState.sendingLocation){
				location.smsBody
			} else {
				smsWithoutLocation
			})
//			sendMultipartTextMessage(number.toString(), null, parts, null, null) //todo
		}
    }

	private val smsWithoutLocation = "l" //TODO()

    private val Location.smsBody: String
        get() = "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"
}