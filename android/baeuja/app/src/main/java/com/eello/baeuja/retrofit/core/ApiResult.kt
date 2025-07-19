package com.eello.baeuja.retrofit.core

import android.util.Log
import com.eello.baeuja.exception.AppError
import retrofit2.Response

data class HttpMeta(
    val code: Int,
    val headers: Map<String, List<String>>
) {
    companion object {
        fun <T> from(response: Response<ApiResponse<T>>) =
            HttpMeta(response.code(), response.headers().toMultimap())
    }
}

sealed class ApiResult<out T> {
    data class Success<T>(val meta: HttpMeta, val body: ApiResponse<T>) : ApiResult<T>()
    data class Failure(
        val meta: HttpMeta,
        val reason: AppError.Api
    ) : ApiResult<Nothing>()

    data class NetworkError(val throwable: Throwable) : ApiResult<Nothing>()
}

suspend inline fun <T, R> ApiResult<T>.handle(
    noinline onSuccess: suspend (ApiResponse<T>) -> R,
    noinline onFailure: suspend (AppError.Api) -> R,
    noinline onNetworkError: ((Throwable) -> R)? = null
): R {
    return handleWithMeta(
        onSuccess = { _, body -> onSuccess(body) },
        onFailure = { _, reason -> onFailure(reason) },
        onNetworkError = onNetworkError
    )
}

suspend inline fun <T, R> ApiResult<T>.handleWithMeta(
    onSuccess: suspend (HttpMeta, ApiResponse<T>) -> R,
    onFailure: suspend (HttpMeta, AppError.Api) -> R,
    noinline onNetworkError: ((Throwable) -> R)? = null
): R {
    return when (this) {
        is ApiResult.Success -> onSuccess(meta, body)
        is ApiResult.Failure -> onFailure(meta, reason)
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