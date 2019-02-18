package com.example.bpawlowski.mvvm.util

import android.content.Context
import android.view.LayoutInflater

val Context.layoutInflater
    get() = LayoutInflater.from(this)