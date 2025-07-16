package com.eello.baeuja.retrofit.api

import com.eello.baeuja.retrofit.core.ApiResponse
import com.eello.baeuja.retrofit.dto.response.UserInfoResponseData
import retrofit2.Response
import retrofit2.http.GET

interface UserAPI {

    @GET("users/me")
    suspend fun fetchUserInfo(): Response<ApiResponse<UserInfoResponseData>>
}