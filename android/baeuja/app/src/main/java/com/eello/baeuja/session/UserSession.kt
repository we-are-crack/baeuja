package com.eello.baeuja.session

import android.content.Context
import com.eello.baeuja.di.AppEntryPoint
import com.eello.baeuja.retrofit.repository.UserRepository
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import java.time.ZoneId
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

enum class SignInType {
    GUEST,
    GOOGLE,
}

data class UserInfo(
    val email: String?,
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
        Timber.d("유저 정보 저장: $userInfo")
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

@Singleton
class UserSessionInitializer @Inject constructor(
    private val userSession: UserSession,
    private val userRepository: UserRepository
) : Initializer {
    override suspend fun initialize() {
        try {
            Timber.d("UserSession 초기화 시도...")
            val userInfo = userRepository.fetchUserInfo()
            userSession.setUserInfo(userInfo)
        } catch (e: Exception) {
            Timber.e(e, "유저 정보 초기화 실패")
            throw e
        }
    }
}