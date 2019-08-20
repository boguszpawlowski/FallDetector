package com.bpawlowski.system.connectivity

import android.content.Context
import com.bpawlowski.core.model.Contact

interface CallService {
	fun call(context: Context, contact: Contact)
}