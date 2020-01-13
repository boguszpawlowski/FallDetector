package com.bpawlowski.domain.coroutines

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object DefaultContextProvider : ContextProvider {
    override val Main: CoroutineContext
        get() = Dispatchers.Main
    override val IO: CoroutineContext
        get() = Dispatchers.IO
    override val Default: CoroutineContext
        get() = Dispatchers.Default
}
