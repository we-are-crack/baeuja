package com.eello.baeuja.data.auth.dto.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
