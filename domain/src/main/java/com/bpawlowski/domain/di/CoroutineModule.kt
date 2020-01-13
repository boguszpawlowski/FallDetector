package com.bpawlowski.domain.di

import com.bpawlowski.domain.coroutines.ContextProvider
import com.bpawlowski.domain.coroutines.DefaultContextProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutineModule = module {
    single<ContextProvider>(named(CONTEXT_PROVIDER_APP)) { DefaultContextProvider }
}
