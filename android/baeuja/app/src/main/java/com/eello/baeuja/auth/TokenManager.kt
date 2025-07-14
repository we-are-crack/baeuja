package com.eello.baeuja.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore(name = "auth_prefs")

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

    private var _accessToken: String? = null
    val accessToken: String? get() = _accessToken

    private var _refreshToken: String? = null
    val refreshToken: String? get() = _refreshToken

    suspend fun loadTokensToMemory() {
        val prefs = context.dataStore.data.first()
        _accessToken = prefs[ACCESS_TOKEN_KEY]
        _refreshToken = prefs[REFRESH_TOKEN_KEY]
    }

    suspend fun saveAccessToken(accessToken: String) {
        _accessToken = accessToken
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
        }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        _refreshToken = refreshToken
        context.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        _accessToken = accessToken
        _refreshToken = refreshToken
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    suspend fun clearTokens() {
        _accessToken = null
        _refreshToken = null
        context.dataStore.edit { it.clear() }
    }
}