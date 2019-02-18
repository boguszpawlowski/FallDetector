package com.example.bpawlowski.mvvm.activity.base.activity

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.bpawlowski.mvvm.di.annotation.AppScope
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider

@AppScope
class ViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?:
        creators.asIterable().firstOrNull{modelClass.isAssignableFrom(it.key)}?.value
            ?: throw IllegalArgumentException("Could`t find ViewModel")

        return try{
            creator.get() as T
        }catch (e: Exception){
            throw RuntimeException(e)
        }
    }
}