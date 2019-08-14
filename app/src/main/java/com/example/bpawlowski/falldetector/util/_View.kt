package com.example.bpawlowski.falldetector.util

import android.view.View
import android.widget.ImageButton

const val ANIMATION_FAST_MILLIS = 50L

fun ImageButton.simulateClick(delay: Long = ANIMATION_FAST_MILLIS) {
	performClick()
	isPressed = true
	invalidate()
	postDelayed({
		invalidate()
		isPressed = false
	}, delay)
}