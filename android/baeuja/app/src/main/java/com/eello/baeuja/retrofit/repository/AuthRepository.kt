package com.eello.baeuja.retrofit.repository

import com.eello.baeuja.auth.AuthResult
import com.eello.baeuja.auth.TokenManager
import com.eello.baeuja.retrofit.ApiResponseCode
import com.eello.baeuja.retrofit.api.AuthAPI
import com.eello.baeuja.retrofit.dto.request.SignInRequestDto
import com.eello.baeuja.retrofit.dto.request.SignUpRequestDto
import com.eello.baeuja.retrofit.dto.request.SignUpRequestDto.SignUpType
import com.eello.baeuja.retrofit.dto.response.SignInResponseDto
import com.eello.baeuja.retrofit.parseError
import com.eello.baeuja.viewmodel.DisplayNameAvailable
import com.eello.baeuja.viewmodel.GoogleSignInUserInfo
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authAPI: AuthAPI,
    private val tokenManager: TokenManager
) {
    suspend fun signIn(googleSignInUserInfo: GoogleSignInUserInfo): AuthResult {
        val requestDto = SignInRequestDto.from(googleSignInUserInfo)
        val response = authAPI.signIn(requestDto)
        if (response.isSuccessful) {
            val body = response.body() ?: throw IllegalStateException("Body is null")
            return when (body.code) {
                ApiResponseCode.SUCCESS -> {
                    val accessToken = body.data.accessToken
                    tokenManager.saveAccessToken(accessToken)

                    AuthResult.Success
                }

                ApiResponseCode.USER_NOT_FOUND -> {
                    AuthResult.Unregistered
                }

                else -> {
                    AuthResult.Failure
                }
            }
        } else {
            val error = parseError<SignInResponseDto>(response.errorBody())
            error?.let {
                if (it.code == ApiResponseCode.USER_NOT_FOUND) {
                    return AuthResult.Unregistered
                }
            }

            throw IllegalStateException("Response is not successful")
        }
    }

    suspend fun guestSignUp(): AuthResult {
        val signUpRequestDto = SignUpRequestDto(loginType = SignUpType.GUEST)
        return signUp(signUpRequestDto)
    }

    suspend fun googleSignUp(
        googleSignInUserInfo: GoogleSignInUserInfo,
        displayName: String
    ): AuthResult {
        val signUpRequestDto = SignUpRequestDto(
            email = googleSignInUserInfo.email,
            nickname = displayName,
            loginType = SignUpType.GOOGLE
        )

        return signUp(signUpRequestDto)
    }

    private suspend fun signUp(signUpRequestDto: SignUpRequestDto): AuthResult {
        val response = authAPI.signUp(signUpRequestDto)
        if (response.isSuccessful) {
            val body = response.body() ?: throw IllegalStateException("Body is null")
            return when (body.code) {
                ApiResponseCode.SUCCESS -> {
                    val accessToken = body.data?.accessToken
                        ?: throw IllegalStateException("Response body is null")
                    tokenManager.saveAccessToken(accessToken)

                    AuthResult.Success
                }

                else -> {
                    AuthResult.Failure
                }
            }
        } else {
            throw IllegalStateException("Response is not successful")
        }
    }

    suspend fun checkDisplayNameAvailable(displayName: String): DisplayNameAvailable {
        val response = authAPI.checkDisplayNameAvailable(displayName)
        val body = response.body() ?: throw IllegalStateException("Body is null")
        return when (body.code) {
            ApiResponseCode.SUCCESS -> DisplayNameAvailable.Available

            else -> {
                val message = body.message
                DisplayNameAvailable.Unavailable(message)
            }
        }
    }
}