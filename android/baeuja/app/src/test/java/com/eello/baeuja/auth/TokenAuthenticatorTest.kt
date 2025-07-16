package com.eello.baeuja.auth

import com.eello.baeuja.fake.BaseFakeTokenManager
import com.eello.baeuja.retrofit.core.ApiResponse
import com.eello.baeuja.retrofit.core.ApiResponseCode
import com.eello.baeuja.retrofit.dto.response.TokenRefreshResponseData
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test


class TokenAuthenticatorTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var gson: Gson

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        // 테스트용 TokenManager와 AuthRepository 구현체를 주입해야 함 (Fake 또는 Mock)
        val fakeTokenManager = FakeTokenManager()
        val fakeTokenRefresher = FakeTokenRefresher()

        okHttpClient = OkHttpClient.Builder()
            .authenticator(TokenAuthenticator(fakeTokenManager, fakeTokenRefresher))
            .build()

        gson = Gson()
    }

    @Test
    fun `401 응답 시 토큰 재발급 후 재요청`() {
        // 1차 응답: 401 Unauthorized
        mockWebServer.enqueue(MockResponse().setResponseCode(401))

        // 2차 응답: 200 OK
        val refreshTokenResponse = ApiResponse<TokenRefreshResponseData>(
            code = ApiResponseCode.SUCCESS,
            message = "success",
            data = TokenRefreshResponseData("reissued_access_token", "valid_refresh_token")
        )

        val jsonBody = gson.toJson(refreshTokenResponse)
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonBody)
        )


        val request = Request.Builder()
            .url(mockWebServer.url("/test"))
            .header("Authorization", "Bearer expired_token")
            .build()

        val response = okHttpClient.newCall(request).execute()

        assert(response.code() == 200)
        println(response.body())

        val recordedRequests = mockWebServer.requestCount
        assert(recordedRequests == 2) // 401 + retry
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}

class FakeTokenManager : BaseFakeTokenManager() {
    override val accessToken: String? get() = "expired_token"
    override val refreshToken: String? get() = "valid_refresh_token"
}

class FakeTokenRefresher : TokenRefresher {
    override suspend fun refreshAccessToken(
        accessToken: String,
        refreshToken: String
    ): String? {
        return "reissued_access_token"
    }
}