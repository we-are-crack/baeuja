package com.eello.baeuja.data.network

import com.eello.baeuja.exception.AppError
import com.eello.baeuja.retrofit.core.ApiResponse
import com.eello.baeuja.retrofit.core.ApiResult
import com.eello.baeuja.retrofit.core.HttpMeta
import okhttp3.Request
import okio.Buffer
import retrofit2.Response
import timber.log.Timber
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

            Timber.Forest.d("응답 코드: $apiResult.code()")

            val httpMeta = HttpMeta.Companion.from(apiResult)
            if (apiResult.isSuccessful) {
                val body = apiResult.body()
                if (body != null) {
                    Timber.Forest.d("성공 응답 본문: $body")
                    ApiResult.Success(httpMeta, body)
                } else {
                    Timber.Forest.w("응답은 성공했지만 본문이 비어 있음.")
                    ApiResult.Failure(httpMeta, AppError.Api.EmptyResponse())
                }
            } else {
                val errorBody = apiErrorBodyParser.parse(apiResult.errorBody())
                Timber.Forest.w("실패 응답 본문: $errorBody")
                ApiResult.Failure(
                    httpMeta,
                    AppError.Api.from(httpMeta.code, errorBody.code.name, errorBody.message)
                )
            }
        } catch (e: Exception) {
            Timber.Forest.e(e, "네트워크 오류 발생: ${e.localizedMessage}")
            ApiResult.NetworkError(e)
        }
    }

    private fun loggingRequestInfo(request: Request) {
        Timber.Forest.d("요청 URL: ${request.url}")

        val requestHeaders = request.headers.toMultimap()
        val requestBody = request.body
        Timber.Forest.d("요청 헤더: $requestHeaders")

        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            val charset = requestBody.contentType()?.charset(Charsets.UTF_8) ?: Charsets.UTF_8
            val bodyString = buffer.readString(charset)
            Timber.Forest.d("요청 본문: $bodyString")
        }
    }
}