package com.eello.baeuja.viewmodel

import android.content.Context
import android.util.Log
import com.eello.baeuja.di.AppEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.ZoneId
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

enum class SignInType {
    GUEST,
    GOOGLE,
}

data class UserInfo(
    val email: String,
    val displayName: String,
    val language: Locale,
    val timezone: ZoneId,
    val loginType: SignInType
)

@Singleton
class UserSession @Inject constructor() {
    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    fun setUserInfo(userInfo: UserInfo) {
        Log.d("UserSession", "setUserInfo: $userInfo")
        _userInfo.value = userInfo
    }
}

object UserSessionProvider {
    fun get(context: Context): UserSession {
        return EntryPointAccessors
            .fromApplication(context, AppEntryPoint::class.java)
            .userSession()
    }
}