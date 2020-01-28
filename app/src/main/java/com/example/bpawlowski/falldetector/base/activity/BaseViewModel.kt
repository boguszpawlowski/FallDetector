@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.example.bpawlowski.falldetector.base.activity

import androidx.lifecycle.ViewModel
import com.example.bpawlowski.falldetector.domain.MviState
import com.example.bpawlowski.falldetector.domain.StateValue
import com.example.bpawlowski.falldetector.domain.StateValue.Success
import com.example.bpawlowski.falldetector.util.requireSnackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import kotlin.reflect.KProperty1

abstract class BaseViewModel<S : MviState>(
    private var state: S
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(state)
    val stateFlow: StateFlow<S> = _stateFlow

    protected val backgroundScope = CoroutineScope(Dispatchers.Default)

    protected val currentState: S
        get() = state

    fun setState(block: S.() -> S) {
        state = state.block()
        _stateFlow.value = state
    }

    fun withState(block: (S) -> Unit) {
        block(state)
    }

    /**
     * Use only if You need to trigger event once (f.e. snackbar, notification), it won't be
     * redelivered on rotation change, and won't emit initial state
     */
    suspend fun <T> subscribe(prop: KProperty1<S, T>, subscriber: (T) -> Unit) =
        stateFlow
            .map { prop.get(it) }
            .distinctUntilChanged()
            .drop(1)
            .collect { subscriber(it) }

    /**
     * Subscribing to all changes in state for this property, use when you need to get state after
     * certain event
     */
    suspend fun <T> subscribeToAll(prop: KProperty1<S, T>, subscriber: (T) -> Unit) =
        stateFlow
            .map { prop.get(it) }
            .distinctUntilChanged()
            .collect { subscriber(it) }
}
