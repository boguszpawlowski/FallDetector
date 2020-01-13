package com.example.bpawlowski.falldetector.screens.main.events

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.widget.NestedScrollView
import com.google.android.gms.maps.SupportMapFragment

class SafeScrollMapFragment : SupportMapFragment() {

    var scrollView: NestedScrollView? = null

    override fun onCreateView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        savedInstance: Bundle?
    ): View? {
        val mapView = super.onCreateView(layoutInflater, viewGroup, savedInstance)

        if (mapView != null) {
            (mapView as ViewGroup).addView(
                TouchInterceptor(requireContext()),
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }

        return mapView
    }

    private inner class TouchInterceptor(context: Context) : FrameLayout(context) {

        override fun dispatchTouchEvent(event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> scrollView?.requestDisallowInterceptTouchEvent(true)
                MotionEvent.ACTION_UP -> scrollView?.requestDisallowInterceptTouchEvent(true)
            }
            return super.dispatchTouchEvent(event)
        }
    }
}