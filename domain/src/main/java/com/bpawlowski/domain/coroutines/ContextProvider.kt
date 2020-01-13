package com.bpawlowski.domain.coroutines

import kotlin.coroutines.CoroutineContext

interface ContextProvider {
    val Main: CoroutineContext
    val IO: CoroutineContext
    val Default: CoroutineContext
}
