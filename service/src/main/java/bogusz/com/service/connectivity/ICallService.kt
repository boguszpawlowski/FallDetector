package bogusz.com.service.connectivity

import android.content.Context
import bogusz.com.service.model.Contact

interface ICallService{
    fun call(context: Context, contact: Contact)
}