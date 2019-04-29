package bogusz.com.service.connectivity

import android.content.Context
import bogusz.com.service.model.Contact
import bogusz.com.service.util.callNumber
import javax.inject.Inject

internal class CallServiceImpl @Inject constructor() : CallService {

    override fun call(context: Context, contact: Contact) = callNumber(context, contact.mobile)
}