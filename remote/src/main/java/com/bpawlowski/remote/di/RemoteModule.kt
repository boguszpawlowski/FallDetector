package com.bpawlowski.remote.di

import com.bpawlowski.core.di.CONTEXT_PROVIDER_APP
import com.bpawlowski.core.di.OK_HTTP_AUTHORIZED
import com.bpawlowski.core.di.RETROFIT_AUTHORIZED
import com.bpawlowski.remote.api.EventApi
import com.bpawlowski.remote.client.EventClient
import com.bpawlowski.remote.client.EventClientImpl
import com.bpawlowski.remote.config.API_ENDPOINT
import com.bpawlowski.remote.config.MAX_TIMEOUT
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule = module {
    single(named(OK_HTTP_AUTHORIZED)) {
        OkHttpClient.Builder().apply {
            retryOnConnectionFailure(false)
            connectTimeout(MAX_TIMEOUT, TimeUnit.SECONDS).readTimeout(MAX_TIMEOUT, TimeUnit.SECONDS)
        }.build()
    }

    single<Retrofit>(named(RETROFIT_AUTHORIZED)) {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(API_ENDPOINT)
            .client(get<OkHttpClient>(named(OK_HTTP_AUTHORIZED)))
            .build()
    }

    factory<EventApi> { get<Retrofit>(named(RETROFIT_AUTHORIZED)).create(EventApi::class.java) }

    single<EventClient>{ EventClientImpl(get(), get(named(CONTEXT_PROVIDER_APP)))}
}
