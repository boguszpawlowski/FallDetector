package com.example.bpawlowski.falldetector.ui.base.recycler

interface SwipeableItem {
    val swipeDirs: Int
    fun onSwipeStarted()
    fun onSwipeEnded()
    fun onDismissed()
}