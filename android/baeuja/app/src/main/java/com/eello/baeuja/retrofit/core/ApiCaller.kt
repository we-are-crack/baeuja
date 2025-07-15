package com.eello.baeuja.retrofit.core

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Response

suspend fun <T> apiCall(
    call: suspend () -> Response<ApiResponse<T>>
): ApiResult<T> {
    return try {
        val apiResult = call()
        Log.d("apiCall", "apiResult: $apiResult")
        if (apiResult.isSuccessful) {
            val body = apiResult.body()
            Log.d("apiCall", "apiResult success body: $body")

            if (body != null) ApiResult.Success(body)
            else ApiResult.Failure(body as ApiResponse<Unit>)
        } else {
            parseErrorBody(apiResult.errorBody())?.let {
                Log.d("apiCall", "apiResult error body: $it")
                ApiResult.Failure(it)
            } ?: ApiResult.NetworkError(IllegalStateException("Error response body is null"))
        }
    } catch (e: Exception) {
        ApiResult.NetworkError(e)
    }
}

fun parseErrorBody(errorBody: ResponseBody?): ApiResponse<Unit>? {
    return try {
        errorBody?.charStream()?.let {
            Gson().fromJson(it, object : TypeToken<ApiResponse<Unit>>() {}.type)
        }
    } catch (e: Exception) {
        null
    }
}