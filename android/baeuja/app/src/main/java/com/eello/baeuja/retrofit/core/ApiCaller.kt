package com.eello.baeuja.retrofit.core

import android.util.Log
import com.eello.baeuja.exception.AppError
import okhttp3.Request
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiCaller @Inject constructor(
    private val apiErrorBodyParser: ApiErrorBodyParser,
) {
    suspend fun <T> call(
        call: suspend () -> Response<ApiResponse<T>>
    ): ApiResult<T> {
        return try {
            val apiResult = call()
            loggingRequestInfo(apiResult.raw().request)

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
                val errorBody = apiErrorBodyParser.parse(apiResult.errorBody())
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

    private fun loggingRequestInfo(request: Request) {
        Log.d("apiCall", "요청 URL: ${request.url}")

        val requestHeaders = request.headers.toMultimap()
        val requestBody = request.body
        Log.d("apiCall", "요청 헤더: $requestHeaders")

        if (requestBody != null) {
            val buffer = okio.Buffer()
            requestBody.writeTo(buffer)
            val charset = requestBody.contentType()?.charset(Charsets.UTF_8) ?: Charsets.UTF_8
            val bodyString = buffer.readString(charset)
            Log.d("apiCall", "요청 본문: $bodyString")
        }
    }
}