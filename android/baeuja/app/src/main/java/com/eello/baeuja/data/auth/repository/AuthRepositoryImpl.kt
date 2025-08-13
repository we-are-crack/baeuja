package com.eello.baeuja.data.auth.repository

import com.eello.baeuja.data.auth.dto.request.SignInRequest
import com.eello.baeuja.data.auth.dto.request.SignUpRequest
import com.eello.baeuja.domain.auth.model.AuthResult
import com.eello.baeuja.domain.auth.repository.AuthRepository
import com.eello.baeuja.domain.auth.service.TokenManager
import com.eello.baeuja.domain.model.user.SignInType
import com.eello.baeuja.exception.AppError
import com.eello.baeuja.exception.AppException
import com.eello.baeuja.retrofit.api.AuthAPI
import com.eello.baeuja.retrofit.core.ApiCaller
import com.eello.baeuja.retrofit.core.ApiResponseCode
import com.eello.baeuja.retrofit.core.handle
import com.eello.baeuja.viewmodel.DisplayNameAvailable
import com.eello.baeuja.viewmodel.GoogleSignInUserInfo
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiCaller: ApiCaller,
    private val authAPI: AuthAPI,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun signIn(googleSignInUserInfo: GoogleSignInUserInfo): AuthResult {
        Timber.Forest.d("서버에 Google 로그인 정보로 서비스 로그인 요청...")
        val requestDto = SignInRequest.Companion.from(googleSignInUserInfo)
        val apiResult = apiCaller.call { authAPI.signIn(requestDto) }
        return apiResult.handle(
            onSuccess = { body ->
                when (body.code) {
                    ApiResponseCode.SUCCESS -> {
                        Timber.Forest.d("서비스 로그인 성공: ${body.data}")
                        val accessToken = body.data?.accessToken ?: error("No token")
                        val refreshToken = body.data.refreshToken
                        tokenManager.saveTokens(accessToken, refreshToken)
                        AuthResult.Success
                    }

                    ApiResponseCode.USER_NOT_FOUND -> {
                        Timber.Forest.d("서비스 로그인 실패: 등록되지 않은 사용자")
                        AuthResult.Unregistered
                    }

                    else -> {
                        Timber.Forest.d(buildString {
                            append("서비스 로그인 실패: ")
                            append("\n\tcode: ${body.code}")
                            append("\n\tmessage: ${body.message}")
                        })
                        AuthResult.Failure()
                    }
                }
            },
            onFailure = { reason ->
                if (reason is AppError.Api.Unauthorized) {
                    AuthResult.Unregistered
                } else AuthResult.Failure(message = reason.message)
            }
        )
    }

    override suspend fun guestSignUp(): AuthResult {
        val signUpRequest = SignUpRequest(loginType = SignInType.GUEST)
        return signUp(signUpRequest)
    }

    override suspend fun googleSignUp(
        googleSignInUserInfo: GoogleSignInUserInfo,
        displayName: String
    ): AuthResult {
        val signUpRequest = SignUpRequest(
            email = googleSignInUserInfo.email,
            nickname = displayName,
            loginType = SignInType.GOOGLE
        )

        return signUp(signUpRequest)
    }

    private suspend fun signUp(signUpRequest: SignUpRequest): AuthResult {
        Timber.Forest.d("${signUpRequest.loginType} 사용자 등록 시도: $signUpRequest")
        val apiResult = apiCaller.call { authAPI.signUp(signUpRequest) }
        return apiResult.handle(
            onSuccess = { body ->
                when (body.code) {
                    ApiResponseCode.SUCCESS -> {
                        Timber.Forest.d("사용자 등록 성공")
                        val accessToken = body.data?.accessToken
                            ?: throw AppException(AppError.Api.EmptyResponse())
                        val refreshToken = body.data.refreshToken

                        tokenManager.saveTokens(
                            accessToken = accessToken,
                            refreshToken = refreshToken
                        )

                        AuthResult.Success
                    }

                    else -> {
                        Timber.Forest.d(buildString {
                            append("사용자 등록 실패: ")
                            append("\n\tcode: ${body.code}")
                            append("\n\tmessage: ${body.message}")
                        })
                        AuthResult.Failure()
                    }
                }
            },
            onFailure = { reason ->
                AuthResult.Failure(message = reason.message)
            }
        )
    }

    override suspend fun checkDisplayNameAvailable(displayName: String): DisplayNameAvailable {
        val apiResult = apiCaller.call { authAPI.checkDisplayNameAvailable(displayName) }
        return apiResult.handle(
            onSuccess = { body ->
                when (body.code) {
                    ApiResponseCode.SUCCESS -> DisplayNameAvailable.Available
                    else -> DisplayNameAvailable.Unavailable(body.message)
                }
            },
            onFailure = { reason ->
                DisplayNameAvailable.Unavailable(reason.message)
            }
        )
    }
}