package com.eello.baeuja.fake

import com.eello.baeuja.domain.auth.service.TokenManager


abstract class BaseFakeTokenManager : TokenManager {
    override val accessToken: String?
        get() = TODO("Not yet implemented")
    override val refreshToken: String?
        get() = TODO("Not yet implemented")

    override suspend fun loadTokensToMemory() {
        TODO("Not yet implemented")
    }

    override suspend fun saveAccessToken(accessToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun clearTokens() {
        TODO("Not yet implemented")
    }
}