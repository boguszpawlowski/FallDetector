package com.bpawlowski.system.connectivity

import android.content.Context
import com.bpawlowski.database.entity.Contact
import com.bpawlowski.system.util.callNumber

internal class CallServiceImpl : CallService {

	override fun call(context: Context, contact: Contact) = callNumber(context, contact.mobile)
}