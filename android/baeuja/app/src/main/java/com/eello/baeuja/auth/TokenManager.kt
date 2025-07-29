package com.eello.baeuja.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "auth_prefs")

interface TokenManager {
    val accessToken: String?
    val refreshToken: String?

    suspend fun loadTokensToMemory()
    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun clearTokens()
}

//@Singleton
class TokenManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TokenManager {
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

    private var _accessToken: String? = null
    override val accessToken: String? get() = _accessToken

    private var _refreshToken: String? = null
    override val refreshToken: String? get() = _refreshToken

    override suspend fun loadTokensToMemory() {
        val prefs = context.dataStore.data.first()
        _accessToken = prefs[ACCESS_TOKEN_KEY]
        _refreshToken = prefs[REFRESH_TOKEN_KEY]
    }

    override suspend fun saveAccessToken(accessToken: String) {
        _accessToken = accessToken
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
        }
        Timber.d("액세스 토큰 저장: $accessToken")
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        _refreshToken = refreshToken
        context.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
        Timber.d("리프레시 토큰 저장: $refreshToken")
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        _accessToken = accessToken
        _refreshToken = refreshToken
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
        Timber.d("토큰 저장: \n\taccessToken: $accessToken \n\trefreshToken: $refreshToken")
    }

    override suspend fun clearTokens() {
        _accessToken = null
        _refreshToken = null
        context.dataStore.edit { it.clear() }
        Timber.d("토큰 클리어:\n\taccessToken: $accessToken \n\trefreshToken: $refreshToken")
    }
}