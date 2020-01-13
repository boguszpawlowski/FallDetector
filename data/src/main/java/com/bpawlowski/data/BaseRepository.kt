package com.bpawlowski.data

import com.bpawlowski.domain.coroutines.ContextProvider
import com.bpawlowski.domain.coroutines.DefaultContextProvider

abstract class BaseRepository(
    private val contextProvider: ContextProvider = DefaultContextProvider
) {
    protected val backgroundContext = contextProvider.Default
    protected val iOContext = contextProvider.IO
}
