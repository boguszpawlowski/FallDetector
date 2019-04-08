package bogusz.com.service.connectivity

import android.content.Context
import javax.inject.Inject

class SmsService @Inject constructor(
    private val context: Context
): ISmsService{

    override fun sendMessage(number: Int, message: String) {

    }
}