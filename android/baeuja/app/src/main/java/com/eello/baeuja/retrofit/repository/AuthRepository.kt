package com.eello.baeuja.retrofit.repository

import com.eello.baeuja.auth.AuthResult
import com.eello.baeuja.auth.TokenManager
import com.eello.baeuja.exception.AppError
import com.eello.baeuja.exception.AppException
import com.eello.baeuja.retrofit.api.AuthAPI
import com.eello.baeuja.retrofit.core.ApiResponseCode
import com.eello.baeuja.retrofit.core.apiCall
import com.eello.baeuja.retrofit.core.handle
import com.eello.baeuja.retrofit.dto.request.SignInRequestDto
import com.eello.baeuja.retrofit.dto.request.SignUpRequestDto
import com.eello.baeuja.viewmodel.DisplayNameAvailable
import com.eello.baeuja.viewmodel.GoogleSignInUserInfo
import com.eello.baeuja.viewmodel.SignInType
import javax.inject.Inject

interface AuthRepository {

    suspend fun signIn(googleSignInUserInfo: GoogleSignInUserInfo): AuthResult
    suspend fun guestSignUp(): AuthResult
    suspend fun googleSignUp(
        googleSignInUserInfo: GoogleSignInUserInfo,
        displayName: String
    ): AuthResult

    suspend fun checkDisplayNameAvailable(displayName: String): DisplayNameAvailable
}

class AuthRepositoryImpl @Inject constructor(
    private val authAPI: AuthAPI,
    private val tokenManager: TokenManager
) : AuthRepository {
    override suspend fun signIn(googleSignInUserInfo: GoogleSignInUserInfo): AuthResult {
        val requestDto = SignInRequestDto.from(googleSignInUserInfo)
        val apiResult = apiCall { authAPI.signIn(requestDto) }
        return apiResult.handle(
            onSuccess = { body ->
                when (body.code) {
                    ApiResponseCode.SUCCESS -> {
                        val accessToken = body.data?.accessToken ?: error("No token")
                        val refreshToken = body.data.refreshToken
                        tokenManager.saveTokens(accessToken, refreshToken)
                        AuthResult.Success
                    }

                    ApiResponseCode.USER_NOT_FOUND -> AuthResult.Unregistered
                    else -> AuthResult.Failure()
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
        val signUpRequestDto = SignUpRequestDto(loginType = SignInType.GUEST)
        return signUp(signUpRequestDto)
    }

    override suspend fun googleSignUp(
        googleSignInUserInfo: GoogleSignInUserInfo,
        displayName: String
    ): AuthResult {
        val signUpRequestDto = SignUpRequestDto(
            email = googleSignInUserInfo.email,
            nickname = displayName,
            loginType = SignInType.GOOGLE
        )

        return signUp(signUpRequestDto)
    }

    private suspend fun signUp(signUpRequestDto: SignUpRequestDto): AuthResult {
        val apiResult = apiCall { authAPI.signUp(signUpRequestDto) }
        return apiResult.handle(
            onSuccess = { body ->
                when (body.code) {
                    ApiResponseCode.SUCCESS -> {
                        val token = body.data?.accessToken
                            ?: throw AppException(AppError.Api.EmptyResponse())
                        tokenManager.saveAccessToken(token)
                        AuthResult.Success
                    }

                    else -> {
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
        val apiResult = apiCall { authAPI.checkDisplayNameAvailable(displayName) }
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