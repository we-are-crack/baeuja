package com.eello.baeuja.retrofit.api

import com.eello.baeuja.retrofit.dto.request.SignInRequestDto
import com.eello.baeuja.retrofit.dto.request.SignUpRequestDto
import com.eello.baeuja.retrofit.dto.response.CheckDisplayNameResponseDto
import com.eello.baeuja.retrofit.dto.response.SignInResponseDto
import com.eello.baeuja.retrofit.dto.response.SignUpResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthAPI {

    @POST("auth/sign-in")
    suspend fun signIn(@Body signInRequestDto: SignInRequestDto): Response<SignInResponseDto>

    @POST("auth/sign-up")
    suspend fun signUp(@Body signUpRequestDto: SignUpRequestDto): Response<SignUpResponseDto>

    @GET("auth/check-nickname")
    suspend fun checkDisplayNameAvailable(
        @Query("nickname") displayName: String
    ): Response<CheckDisplayNameResponseDto>
}