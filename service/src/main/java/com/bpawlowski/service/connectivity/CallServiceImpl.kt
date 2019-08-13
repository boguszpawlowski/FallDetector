package com.bpawlowski.service.connectivity

import android.content.Context
import com.bpawlowski.service.model.Contact
import com.bpawlowski.service.util.callNumber

internal class CallServiceImpl : CallService {

    override fun call(context: Context, contact: Contact) = callNumber(context, contact.mobile)
}