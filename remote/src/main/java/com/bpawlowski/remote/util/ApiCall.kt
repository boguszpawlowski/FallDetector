package com.bpawlowski.remote.util

import com.bpawlowski.domain.Result
import com.bpawlowski.domain.exception.FallDetectorException
import com.bpawlowski.domain.failure
import com.bpawlowski.domain.success
import retrofit2.Response

fun <T> call(response: Response<T>): Result<T> =
    try {

        if (response.isSuccessful) {
            val data = response.body() ?: UnsupportedOperationException()
            success(data as T)
        } else {
            failure(
                FallDetectorException.ApiException(
                    url = response.raw().request.url.toString(),
                    code = response.code().toString(),
                    errorBody = response.errorBody().toString()
                )
            )
        }
    } catch (e: Exception) {
        failure(FallDetectorException.Unknown(e))
    }
