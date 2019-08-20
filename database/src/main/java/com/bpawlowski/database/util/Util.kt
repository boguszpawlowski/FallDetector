package com.bpawlowski.database.util

import android.os.Handler
import android.os.Looper

fun postDelayed(delay: Long = 150L, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(block, delay)
}