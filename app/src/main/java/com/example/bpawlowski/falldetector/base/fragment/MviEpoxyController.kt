package com.example.bpawlowski.falldetector.base.fragment

import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.example.bpawlowski.falldetector.domain.MviState

/**
 * For use with [com.example.bpawlowski.falldetector.base.fragment.BaseFragment.epoxyController].
 *
 * This builds Epoxy models in a background thread.
 */
open class MviEpoxyController(
    val buildModelsCallback: EpoxyController.() -> Unit = {}
) : AsyncEpoxyController() {

    override fun buildModels() {
        buildModelsCallback()
    }
}

/**
 * Create a [MviEpoxyController] that builds models with the given callback.
 */
fun <S : MviState> BaseFragment<S>.simpleController(
    buildModels: EpoxyController.() -> Unit
) = MviEpoxyController {
    // Models are built asynchronously, so it is possible that this is called after the fragment
    // is detached under certain race conditions.
    if (view == null || isRemoving) return@MviEpoxyController
    buildModels()
}

/**
 * Create a [MviEpoxyController] that builds models with the given callback.
 * When models are built the current state of the viewmodel will be provided.
 */
fun <S : MviState> BaseFragment<S>.simpleController(
    buildModels: EpoxyController.(state: S) -> Unit
) = MviEpoxyController {
    if (view == null || isRemoving) return@MviEpoxyController
    withState { state ->
        buildModels(state)
    }
}

fun <S : MviState> BaseFragment<S>.withState(block: (S) -> Unit) {
    viewModel.withState { block(it) }
}
