package com.eello.baeuja.retrofit.core

import android.util.Log
import com.eello.baeuja.exception.AppError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Response

private fun loggingRequestInfo(request: Request) {
    Log.d("apiCall", "요청 URL: ${request.url()}")

    val requestHeaders = request.headers().toMultimap()
    val requestBody = request.body()
    Log.d("apiCall", "요청 헤더: $requestHeaders")

    if (requestBody != null) {
        val buffer = okio.Buffer()
        requestBody.writeTo(buffer)
        val charset = requestBody.contentType()?.charset(Charsets.UTF_8) ?: Charsets.UTF_8
        val bodyString = buffer.readString(charset)
        Log.d("apiCall", "요청 본문: $bodyString")
    }
}

suspend fun <T> apiCall(
    call: suspend () -> Response<ApiResponse<T>>
): ApiResult<T> {
    return try {
        val apiResult = call()
        loggingRequestInfo(apiResult.raw().request())

        val statusCode = apiResult.code()
        val statusMessage = apiResult.message()
        Log.d("apiCall", "응답 코드: $statusCode, 메시지: $statusMessage")

        val httpMeta = HttpMeta.from(apiResult)
        if (apiResult.isSuccessful) {
            val body = apiResult.body()
            if (body != null) {
                Log.d("apiCall", "성공 응답 본문: $body")
                ApiResult.Success(httpMeta, body)
            } else {
                Log.w("apiCall", "응답은 성공했지만 본문이 비어 있습니다.")
                ApiResult.Failure(httpMeta, AppError.Api.EmptyResponse())
            }
        } else {
            val errorBody = parseErrorBody(apiResult.errorBody())
            Log.w("apiCall", "실패 응답 본문: $errorBody")
            ApiResult.Failure(
                httpMeta,
                AppError.Api.from(httpMeta.code, errorBody.code.name, errorBody.message)
            )
        }
    } catch (e: Exception) {
        Log.e("apiCall", "네트워크 오류 발생: ${e.localizedMessage}", e)
        ApiResult.NetworkError(e)
    }
}

fun parseErrorBody(errorBody: ResponseBody?): ApiResponse<Unit> {
    return try {
        errorBody?.charStream()?.let {
            val type = object : TypeToken<ApiResponse<Unit>>() {}.type
            val parsed = Gson().fromJson<ApiResponse<Unit>>(it, type)
            Log.d("parseErrorBody", "에러 응답 본문 파싱 성공: $parsed")
            parsed
        } ?: run {
            Log.w("parseErrorBody", "에러 본문이 없거나 null입니다. 기본 응답으로 대체합니다.")
            ApiResponse.empty()
        }
    } catch (e: Exception) {
        val raw = errorBody?.string()
        Log.e("parseErrorBody", "에러 본문 파싱 중 예외 발생. 원본 내용: $raw", e)
        Log.w("parseErrorBody", "파싱 실패로 기본 ApiResponse로 대체합니다.")

        ApiResponse(
            code = ApiResponseCode.UNKNOWN,
            message = "에러 파싱 실패: ${raw?.take(100)}",
            data = null
        )
    }
}