package com.bpawlowski.service.connectivity

import android.content.Context
import com.bpawlowski.service.model.Contact

interface CallService{
    fun call(context: Context, contact: Contact)
}