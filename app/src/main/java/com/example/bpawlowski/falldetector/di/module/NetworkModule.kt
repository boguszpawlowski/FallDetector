package com.example.bpawlowski.falldetector.di.module

import com.example.bpawlowski.falldetector.di.annotation.AppScope
import com.example.bpawlowski.falldetector.network.PostApi
import com.example.bpawlowski.falldetector.util.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    @AppScope
    fun providePostApi(retrofit: Retrofit): PostApi {
        return retrofit.create(PostApi::class.java)
    }

    @Provides
    @AppScope
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}




