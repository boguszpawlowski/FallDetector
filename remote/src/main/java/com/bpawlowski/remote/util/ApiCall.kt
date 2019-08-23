package com.bpawlowski.remote.util

import com.bpawlowski.core.domain.Result
import com.bpawlowski.core.domain.failure
import com.bpawlowski.core.domain.success
import retrofit2.Response

 fun <T> Response<T>.call(): Result<T> =
    if(isSuccessful){
        val data = body() ?: UnsupportedOperationException()
        success(data as T)
    } else {
        failure(Exception(errorBody().toString()))
    }
