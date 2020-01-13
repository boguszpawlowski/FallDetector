package com.bpawlowski.system.util

import android.content.Context
import android.content.Intent
import android.net.Uri

internal fun callNumber(context: Context, number: Int) =
    with(Intent(Intent.ACTION_CALL)) {
        data = Uri.parse("tel:$number")
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(this)
    }