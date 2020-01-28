@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.example.bpawlowski.falldetector.util

import android.view.View
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object SnackbarManager {
    private val _messageFlow = MutableSharedFlow<SnackbarPayload>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )
    val messageFlow: SharedFlow<SnackbarPayload> get() = _messageFlow

    fun tryToShowSnackbar(snackbarPayload: SnackbarPayload) {
        _messageFlow.tryEmit(snackbarPayload)
    }

    suspend fun showSnackbar(snackbarPayload: SnackbarPayload) {
        _messageFlow.emit(snackbarPayload)
    }
}

data class SnackbarPayload(
    val message: String,
    val actionResId: Int?,
    val actionListener: ((View) -> Unit)?
)

/**
 * Helper function for showing snackbar. Note that this don't guarantee that the snackbar will be shown.
 * If you need to be sure of that, use suspending [requireSnackbar] function instead.
 */
fun snackbar(
    message: String,
    actionResId: Int? = null,
    actionListener: ((View) -> Unit)? = null
) = SnackbarManager.tryToShowSnackbar(SnackbarPayload(message, actionResId, actionListener))

suspend fun requireSnackbar(
    message: String,
    actionResId: Int? = null,
    actionListener: ((View) -> Unit)? = null
) = SnackbarManager.showSnackbar(SnackbarPayload(message, actionResId, actionListener))
