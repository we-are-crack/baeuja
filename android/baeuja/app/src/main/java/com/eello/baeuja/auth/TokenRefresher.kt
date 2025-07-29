package com.eello.baeuja.auth

import com.eello.baeuja.exception.AppError
import com.eello.baeuja.exception.AppException
import com.eello.baeuja.retrofit.api.AuthAPI
import com.eello.baeuja.retrofit.core.ApiCaller
import com.eello.baeuja.retrofit.core.handle
import com.eello.baeuja.retrofit.dto.request.TokenRefreshRequestDto
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

interface TokenRefresher {
    suspend fun refreshAccessToken(accessToken: String, refreshToken: String): String?
}

class TokenRefresherImpl @Inject constructor(
    @Named("refresh") private val authAPI: AuthAPI,
    private val tokenManager: TokenManager,
    private val apiCaller: ApiCaller
) : TokenRefresher {

    override suspend fun refreshAccessToken(
        accessToken: String,
        refreshToken: String
    ): String {
        Timber.d("액세스 토큰 재발급 요청 시도...")
        val requestDto = TokenRefreshRequestDto(accessToken, refreshToken)
        val apiResult = apiCaller.call { authAPI.refreshAccessToken(requestDto) }
        return apiResult.handle(
            onSuccess = {
                Timber.d("액세스 토큰 재발급 성공")
                val (accessToken, refreshToken) = it.data
                    ?: throw AppException(reason = AppError.Api.EmptyResponse())
                tokenManager.saveTokens(accessToken, refreshToken)
                accessToken
            },
            onFailure = { reason ->
                throw AppException(reason = reason)
            }
        )
    }
}