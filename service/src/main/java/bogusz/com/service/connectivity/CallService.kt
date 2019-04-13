package bogusz.com.service.connectivity

import android.content.Context
import bogusz.com.service.model.Contact
import bogusz.com.service.util.callNumber
import javax.inject.Inject

class CallService @Inject constructor(
    private val context: Context
) : ICallService {

    override fun call(context: Context, contact: Contact) = callNumber(context, contact.mobile)
}