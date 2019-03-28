package com.example.bpawlowski.falldetector.presentation.util

import android.widget.EditText

val EditText.value: String
    get() = text.toString()