@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.example.bpawlowski.falldetector.base.activity

import androidx.lifecycle.ViewModel
import com.example.bpawlowski.falldetector.domain.MviState
import com.example.bpawlowski.falldetector.domain.StateValue
import com.example.bpawlowski.falldetector.domain.StateValue.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import timber.log.Timber
import kotlin.reflect.KProperty1

abstract class BaseViewModel<S : MviState>(
    private var state: S
) : ViewModel() {

    private val stateChannel = ConflatedBroadcastChannel<S>()

    protected val backgroundScope = CoroutineScope(Dispatchers.Default)

    protected val currentState: S
        get() = state

    val stateFlow = stateChannel.asFlow()

    fun setState(block: S.() -> S) {
        state = state.block()
        stateChannel.offer(state)
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

    suspend fun <T : StateValue<*>> subscribeOnce(prop: KProperty1<S, T>, subscriber: (T) -> Unit) =
        stateFlow
            .map { prop.get(it) }
            .filter { it is Success<*> }
            .take(1)
            .collect { subscriber(it) }

    override fun onCleared() {
        Timber.tag(javaClass.simpleName).v("ON_CLEARED")
        super.onCleared()
    }
}
