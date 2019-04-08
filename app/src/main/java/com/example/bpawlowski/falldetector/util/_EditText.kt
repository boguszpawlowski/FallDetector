package com.example.bpawlowski.falldetector.util

import android.widget.EditText

val EditText.value: String
    get() = text.toString()