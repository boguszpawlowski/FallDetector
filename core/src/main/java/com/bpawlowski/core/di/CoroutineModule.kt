package com.bpawlowski.core.di

import com.bpawlowski.core.coroutines.ContextProvider
import com.bpawlowski.core.coroutines.ContextProviderImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutineModule = module {
    single<ContextProvider>(named(CONTEXT_PROVIDER_APP)) { ContextProviderImpl }
}