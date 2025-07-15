package com.eello.baeuja.retrofit.core

data class ApiResponse<T>(
    val code: ApiResponseCode,
    val message: String,
    val data: T?
)