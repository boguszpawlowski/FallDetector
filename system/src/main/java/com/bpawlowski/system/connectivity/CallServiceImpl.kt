package com.bpawlowski.system.connectivity

import android.content.Context
import com.bpawlowski.domain.model.Contact
import com.bpawlowski.domain.service.CallService
import com.bpawlowski.system.util.callNumber

internal class CallServiceImpl(
    private val context: Context
) : CallService {

    override fun call(contact: Contact) = callNumber(context, contact.mobile)
}
