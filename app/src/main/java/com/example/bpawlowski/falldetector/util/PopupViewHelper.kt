package com.example.bpawlowski.falldetector.util

import android.view.View
import android.widget.PopupMenu

fun showPopupMenu(parentView: View, menuResId: Int, menuClickListener: PopupMenu.OnMenuItemClickListener? = null): PopupMenu =
	PopupMenu(parentView.context, parentView).apply {
		menuInflater.inflate(menuResId, this.menu)
		setOnMenuItemClickListener(menuClickListener)
		show()
	}