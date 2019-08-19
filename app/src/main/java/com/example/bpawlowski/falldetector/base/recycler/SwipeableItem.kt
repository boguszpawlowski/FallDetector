package com.example.bpawlowski.falldetector.base.recycler

interface SwipeableItem {
    val swipeDirs: Int
    fun onSwipeStarted()
    fun onSwipeEnded()
    fun onDismissed()
}