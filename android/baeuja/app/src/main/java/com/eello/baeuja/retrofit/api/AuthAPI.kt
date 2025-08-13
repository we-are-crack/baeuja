package com.eello.baeuja.retrofit.api

import com.eello.baeuja.data.auth.dto.request.SignInRequest
import com.eello.baeuja.data.auth.dto.request.SignUpRequest
import com.eello.baeuja.data.auth.dto.request.TokenRefreshRequest
import com.eello.baeuja.data.auth.dto.response.TokenResponse
import com.eello.baeuja.retrofit.core.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthAPI {

    @POST("auth/sign-in")
    suspend fun signIn(@Body signInRequest: SignInRequest):
            Response<ApiResponse<TokenResponse>>

    @POST("auth/sign-up")
    suspend fun signUp(@Body signUpRequest: SignUpRequest):
            Response<ApiResponse<TokenResponse>>

    @GET("auth/check-nickname")
    suspend fun checkDisplayNameAvailable(@Query("nickname") displayName: String):
            Response<ApiResponse<Unit>>

    @POST("auth/token")
    suspend fun refreshAccessToken(@Body tokenRefreshRequest: TokenRefreshRequest):
            Response<ApiResponse<TokenResponse>>
}