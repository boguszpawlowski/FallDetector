package bogusz.com.service.connectivity

import android.content.Context
import bogusz.com.service.model.Contact

interface CallService{
    fun call(context: Context, contact: Contact)
}