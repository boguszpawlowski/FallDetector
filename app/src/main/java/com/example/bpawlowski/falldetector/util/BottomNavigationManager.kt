@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.example.bpawlowski.falldetector.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object BottomNavigationManager {
    private val mutex = Mutex()

    private val _flow = MutableStateFlow(BottomNavigationConfiguration(true, true))
    val flow: StateFlow<BottomNavigationConfiguration> = _flow

    fun toggleNavigation(navConfiguration: BottomNavigationConfiguration) =
        CoroutineScope(Dispatchers.Default).launch {
            mutex.withLock { _flow.value = navConfiguration }
        }
}

data class BottomNavigationConfiguration(
    val showBottomNaigation: Boolean,
    val showActionBar: Boolean
)

fun toggleNavigation(shouldShowNavigation: Boolean, shouldShowActionBar: Boolean) {
    BottomNavigationManager.toggleNavigation(
        BottomNavigationConfiguration(
            shouldShowNavigation,
            shouldShowActionBar
        )
    )
}
