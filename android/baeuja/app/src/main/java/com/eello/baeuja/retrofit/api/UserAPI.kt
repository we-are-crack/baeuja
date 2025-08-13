package com.eello.baeuja.retrofit.api

import com.eello.baeuja.data.dto.response.UserInfoResponse
import com.eello.baeuja.retrofit.core.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserAPI {

    @GET("users/me")
    suspend fun fetchUserInfo(): Response<ApiResponse<UserInfoResponse>>
}