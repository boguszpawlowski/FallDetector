package com.example.bpawlowski.falldetector.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun sendSms(context: Context, number: Int, message: String) =
    with(Intent(Intent.ACTION_VIEW)) {
        data = Uri.parse("sms:$number")
        putExtra("sms_body", message)
        context.startActivity(this)
    }

fun callNumber(context: Context, number: Int) =
    with(Intent(Intent.ACTION_CALL)) {
        data = Uri.parse("tel:$number")
        context.startActivity(this)
    }