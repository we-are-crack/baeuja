package com.eello.baeuja.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eello.baeuja.auth.TokenManager
import com.eello.baeuja.exception.AppException
import com.eello.baeuja.retrofit.repository.UserRepository
import com.eello.baeuja.session.HomeContentSession
import com.eello.baeuja.session.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val userSession: UserSession,
    private val userRepository: UserRepository,
    private val homeContentSession: HomeContentSession
) : ViewModel() {

    private val _isSignedIn = MutableStateFlow(false)
    val isSignedIn: StateFlow<Boolean> = _isSignedIn

    private val _isCheckCompleted = MutableStateFlow(false)
    val isCheckCompleted: StateFlow<Boolean> = _isCheckCompleted

    private val _isLoadingCompleted = MutableStateFlow(false)
    val isLoadingCompleted: StateFlow<Boolean> = _isLoadingCompleted

    fun checkSignInStatus() {
        Timber.d("로그인 상태 체크 확인 시도...")
        viewModelScope.launch {
            if (tokenManager.refreshToken == null) {
                _isCheckCompleted.value = true
                Timber.d("유저 인증 실패: 토큰 없음")
                return@launch
            }

            try {
                userSession.setUserInfo(userRepository.fetchUserInfo())
                Timber.d("유저 인증 성공: ${userSession.userInfo.value}")
                _isSignedIn.value = true
            } catch (e: AppException) {
                Timber.d("유저 인증 실패: ${e.reason}")
                tokenManager.clearTokens()
            } finally {
                Timber.d("로그인 상태 체크 확인 완료")
                _isCheckCompleted.value = true
            }
        }
    }

    fun loadHomeContents() {
        Timber.d("홈 콘텐츠 로딩 시도...")
        if (!_isCheckCompleted.value) {
            Timber.d("홈 콘텐츠 로딩 실패: 사용자 인증 진행 중")
        }

        if (!_isSignedIn.value) {
            Timber.d("홈 콘텐츠 로딩 실패: 사용자 인증 실패")
        }

        viewModelScope.launch {
            homeContentSession.initialize()
            _isLoadingCompleted.value = true
            Timber.d("홈 콘텐츠 로딩 완료")
        }
    }
}