package com.eello.baeuja.retrofit.dto.response

import com.eello.baeuja.retrofit.ApiResponseCode

data class CheckDisplayNameResponseDto(
    val code: ApiResponseCode,
    val message: String,
    val data: Data?
) {
    class Data()
}