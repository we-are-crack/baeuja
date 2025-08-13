package com.eello.baeuja.data.auth.dto.request

data class TokenRefreshRequest(
    val accessToken: String,
    val refreshToken: String
)