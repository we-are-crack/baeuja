package com.eello.baeuja.retrofit.core

data class ApiResponse<T>(
    val code: ApiResponseCode,
    val message: String,
    val data: T?
) {
    companion object {
        fun empty(): ApiResponse<Unit> = ApiResponse(
            code = ApiResponseCode.UNKNOWN,
            message = "응답 바디가 없음",
            data = null
        )
    }
}