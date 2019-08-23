package com.bpawlowski.core.coroutines

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

internal object ContextProviderImpl : ContextProvider {
    override val Main: CoroutineContext
        get() = Dispatchers.Main
    override val IO: CoroutineContext
        get() = Dispatchers.IO
    override val Default: CoroutineContext
        get() = Dispatchers.Default
}
