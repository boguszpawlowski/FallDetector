package com.bpawlowski.system.connectivity

import android.content.Context
import com.bpawlowski.database.entity.Contact

interface CallService {
	fun call(context: Context, contact: Contact)
}