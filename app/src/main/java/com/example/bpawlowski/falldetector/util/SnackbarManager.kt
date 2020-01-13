@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.example.bpawlowski.falldetector.util

import android.view.View
import kotlinx.coroutines.channels.Channel

object SnackbarManager {
    val messageChannel = Channel<SnackbarPayload>()

    fun showSnackbar(snackbarPayload: SnackbarPayload) {
        messageChannel.offer(snackbarPayload)
    }
}

data class SnackbarPayload(
    val message: String,
    val actionResId: Int?,
    val actionListener: ((View) -> Unit)?
)

fun snackbar(
    message: String,
    actionResId: Int? = null,
    actionListener: ((View) -> Unit)? = null
) = SnackbarManager.showSnackbar(SnackbarPayload(message, actionResId, actionListener))
