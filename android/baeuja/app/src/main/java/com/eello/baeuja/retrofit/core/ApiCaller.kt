package com.eello.baeuja.retrofit.core

import com.eello.baeuja.exception.AppError
import okhttp3.Request
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

            Timber.d("응답 코드: $apiResult.code()")

            val httpMeta = HttpMeta.from(apiResult)
            if (apiResult.isSuccessful) {
                val body = apiResult.body()
                if (body != null) {
                    Timber.d("성공 응답 본문: $body")
                    ApiResult.Success(httpMeta, body)
                } else {
                    Timber.w("응답은 성공했지만 본문이 비어 있음.")
                    ApiResult.Failure(httpMeta, AppError.Api.EmptyResponse())
                }
            } else {
                val errorBody = apiErrorBodyParser.parse(apiResult.errorBody())
                Timber.w("실패 응답 본문: $errorBody")
                ApiResult.Failure(
                    httpMeta,
                    AppError.Api.from(httpMeta.code, errorBody.code.name, errorBody.message)
                )
            }
        } catch (e: Exception) {
            Timber.e(e, "네트워크 오류 발생: ${e.localizedMessage}")
            ApiResult.NetworkError(e)
        }
    }

    private fun loggingRequestInfo(request: Request) {
        Timber.d("요청 URL: ${request.url}")

        val requestHeaders = request.headers.toMultimap()
        val requestBody = request.body
        Timber.d("요청 헤더: $requestHeaders")

        if (requestBody != null) {
            val buffer = okio.Buffer()
            requestBody.writeTo(buffer)
            val charset = requestBody.contentType()?.charset(Charsets.UTF_8) ?: Charsets.UTF_8
            val bodyString = buffer.readString(charset)
            Timber.d("요청 본문: $bodyString")
        }
    }
}