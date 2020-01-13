package com.bpawlowski.remote.di

import com.bpawlowski.domain.config.API_ENDPOINT
import com.bpawlowski.domain.config.MAX_TIMEOUT
import com.bpawlowski.domain.di.OK_HTTP_AUTHORIZED
import com.bpawlowski.domain.di.RETROFIT_AUTHORIZED
import com.bpawlowski.data.datasource.EventRemoteDataSource
import com.bpawlowski.remote.api.EventApi
import com.bpawlowski.remote.client.EventRemoteDataSourceImpl
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RemoteModule {

    private val modules = listOf(configModule, apiModule, dataSourceModule)

    private var loaded = false

    fun load() {
        if (loaded.not()) {
            loadKoinModules(modules)
            loaded = true
        }
    }

    fun unload() {
        if (loaded) {
            unloadKoinModules(modules)
            loaded = false
        }
    }
}

val configModule = module {
    single(named(OK_HTTP_AUTHORIZED)) {
        OkHttpClient.Builder().apply {
            retryOnConnectionFailure(false)
            connectTimeout(MAX_TIMEOUT, TimeUnit.SECONDS).readTimeout(MAX_TIMEOUT, TimeUnit.SECONDS)
        }.addInterceptor(getInterceptor())
            .build()
    }

    single<Retrofit>(named(RETROFIT_AUTHORIZED)) {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(API_ENDPOINT)
            .client(get<OkHttpClient>(named(OK_HTTP_AUTHORIZED)))
            .build()
    }
}

private val apiModule = module {
    single<EventApi> { get<Retrofit>(named(RETROFIT_AUTHORIZED)).create(EventApi::class.java) }
}

private val dataSourceModule = module {
    single<EventRemoteDataSource> {
        EventRemoteDataSourceImpl(get())
    }
}

private fun getInterceptor() = object : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("Connection", "close").build()
        return chain.proceed(request)
    }
}
