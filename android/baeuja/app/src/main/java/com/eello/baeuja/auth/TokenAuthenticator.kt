package com.eello.baeuja.auth

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val tokenRefresher: TokenRefresher
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // 401이 반복되면 중단
        if (responseCount(response) >= 2) {
            return null
        }

        val newAccessToken = runBlocking {
            // 동기적으로 토큰 재발급 요청
            val accessToken = tokenManager.accessToken ?: return@runBlocking null
            val refreshToken = tokenManager.refreshToken ?: return@runBlocking null
            tokenRefresher.refreshAccessToken(accessToken, refreshToken)
        }

        return newAccessToken?.let {
            response.request().newBuilder()
                .header("Authorization", "Bearer $it")
                .build()
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var priorResponse = response.priorResponse()
        while (priorResponse != null) {
            count++
            priorResponse = priorResponse.priorResponse()
        }
        return count
    }
}