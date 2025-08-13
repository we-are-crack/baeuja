package com.eello.baeuja.domain.auth.service

interface TokenManager {
    val accessToken: String?
    val refreshToken: String?

    suspend fun loadTokensToMemory()
    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun clearTokens()
}