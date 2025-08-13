package com.eello.baeuja.domain.auth.service

interface TokenRefresher {

    suspend fun refreshAccessToken(accessToken: String, refreshToken: String): String?
}