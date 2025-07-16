package com.eello.baeuja.retrofit.api

import com.eello.baeuja.retrofit.core.ApiResponse
import com.eello.baeuja.retrofit.dto.request.SignInRequestDto
import com.eello.baeuja.retrofit.dto.request.SignUpRequestDto
import com.eello.baeuja.retrofit.dto.request.TokenRefreshRequestDto
import com.eello.baeuja.retrofit.dto.response.SignInResponseData
import com.eello.baeuja.retrofit.dto.response.SignUpResponseData
import com.eello.baeuja.retrofit.dto.response.TokenRefreshResponseData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthAPI {

    @POST("auth/sign-in")
    suspend fun signIn(@Body signInRequestDto: SignInRequestDto):
            Response<ApiResponse<SignInResponseData>>

    @POST("auth/sign-up")
    suspend fun signUp(@Body signUpRequestDto: SignUpRequestDto):
            Response<ApiResponse<SignUpResponseData>>

    @GET("auth/check-nickname")
    suspend fun checkDisplayNameAvailable(@Query("nickname") displayName: String):
            Response<ApiResponse<Unit>>

    @POST("auth/token")
    suspend fun refreshAccessToken(@Body tokenRefreshRequestDto: TokenRefreshRequestDto):
            Response<ApiResponse<TokenRefreshResponseData>>
}