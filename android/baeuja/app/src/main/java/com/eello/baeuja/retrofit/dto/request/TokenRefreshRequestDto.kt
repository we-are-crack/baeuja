package com.eello.baeuja.retrofit.dto.request

data class TokenRefreshRequestDto(
    val accessToken: String,
    val refreshToken: String
)