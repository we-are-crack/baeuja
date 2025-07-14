package com.eello.baeuja.retrofit.dto.response

import com.eello.baeuja.retrofit.ApiResponseCode

data class SignInResponseDto(
    val code: ApiResponseCode,
    val message: String,
    val data: Data
) {
    data class Data(
        val accessToken: String,
    )
}