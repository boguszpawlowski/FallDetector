package com.example.bpawlowski.falldetector.presentation.util

import android.databinding.BindingAdapter
import android.view.View

@BindingAdapter(value = ["visible"])
fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}