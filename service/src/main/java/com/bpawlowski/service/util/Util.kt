package com.bpawlowski.service.util

import android.os.Handler
import android.os.Looper

val doNothing = Unit

fun postDelayed(delay: Long = 150L, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(block, delay)
}