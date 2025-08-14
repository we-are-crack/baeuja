package com.eello.baeuja.data.network

import com.eello.baeuja.retrofit.core.ApiResponse
import com.eello.baeuja.retrofit.core.ApiResult
import retrofit2.Response

interface ApiCallerHolder {
    val apiCaller: ApiCaller
}

suspend fun <T> ApiCallerHolder.callApi(
    block: suspend () -> Response<ApiResponse<T>>
): ApiResult<T> {
    return apiCaller.call { block() }
}
