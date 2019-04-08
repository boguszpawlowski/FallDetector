package bogusz.com.service.connectivity

import android.content.Context
import javax.inject.Inject

class CallService @Inject constructor(
    private val context: Context
): ICallService{

    override fun call(number: Int) {

    }
}