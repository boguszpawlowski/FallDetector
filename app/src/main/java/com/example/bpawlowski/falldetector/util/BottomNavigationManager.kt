@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.example.bpawlowski.falldetector.util

import kotlinx.coroutines.channels.ConflatedBroadcastChannel

object BottomNavigationManager {
    val broadcastChannel = ConflatedBroadcastChannel<BottomNavigationConfiguration>()

    fun toggleNavigation(navConfiguration: BottomNavigationConfiguration) {
        broadcastChannel.offer(navConfiguration)
    }
}

data class BottomNavigationConfiguration(
    val showBottomNaigation: Boolean,
    val showActionBar: Boolean
)

fun toggleNavigation(shouldShowNavigation: Boolean, shouldShowActionBar: Boolean) {
    BottomNavigationManager.toggleNavigation(BottomNavigationConfiguration(
        shouldShowNavigation,
        shouldShowActionBar
    ))
}
