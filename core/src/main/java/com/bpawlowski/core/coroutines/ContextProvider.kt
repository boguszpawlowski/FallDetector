package com.bpawlowski.core.coroutines

import kotlin.coroutines.CoroutineContext

interface ContextProvider{
    val Main: CoroutineContext
    val IO: CoroutineContext
    val Default: CoroutineContext
}