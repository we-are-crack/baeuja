package com.eello.baeuja.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eello.baeuja.auth.TokenManager
import com.eello.baeuja.exception.AppException
import com.eello.baeuja.retrofit.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val userSession: UserSession,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _isSignedIn = MutableStateFlow(false)
    val isSignedIn: StateFlow<Boolean> = _isSignedIn

    private val _isCheckCompleted = MutableStateFlow(false)
    val isCheckCompleted: StateFlow<Boolean> = _isCheckCompleted

    fun checkSignInStatus() {
        viewModelScope.launch {
            if (tokenManager.refreshToken == null) {
                _isCheckCompleted.value = true
                return@launch
            }

            try {
                userSession.setUserInfo(userRepository.fetchUserInfo())
                Log.i("SplashViewModel", "유저 인증 성공: ${userSession.userInfo.value}")
                _isSignedIn.value = true
            } catch (e: AppException) {
                Log.w("SplashViewModel", "유저 인증 실패: ${e.reason}")
                tokenManager.clearTokens()
            } finally {
                _isCheckCompleted.value = true
            }
        }
    }
}