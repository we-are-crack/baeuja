package com.eello.baeuja.retrofit.core

import android.util.Log

sealed class ApiResult<out T> {
    data class Success<T>(val body: ApiResponse<T>) : ApiResult<T>()
    data class Failure(val error: ApiResponse<Unit>) : ApiResult<Nothing>()
    data class NetworkError(val throwable: Throwable) : ApiResult<Nothing>()
}

suspend inline fun <T, R> ApiResult<T>.handle(
    onSuccess: suspend (ApiResponse<T>) -> R,
    onFailure: suspend (ApiResponse<Unit>) -> R,
    noinline onNetworkError: ((Throwable) -> R)? = null
): R {
    return when (this) {
        is ApiResult.Success -> onSuccess(body)
        is ApiResult.Failure -> onFailure(error)
        is ApiResult.NetworkError -> {
            if (onNetworkError != null) {
                onNetworkError(throwable)
            } else {
                // 기본 처리 로직
                Log.e("NetworkError", "Network error occurred: ${throwable.message}")
                throw throwable
            }
        }
    }
}