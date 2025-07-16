package com.eello.baeuja.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eello.baeuja.auth.TokenManager
import com.eello.baeuja.retrofit.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignedInState(
    val isSignedIn: Boolean = false,
    val isCheckCompleted: Boolean = false
)

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


    private val _signedInState = MutableStateFlow(SignedInState())
    val signedInState: StateFlow<SignedInState> = _signedInState

    fun checkSignInStatus() {
        viewModelScope.launch {
            if (tokenManager.refreshToken == null) {
                _isCheckCompleted.value = true
                return@launch
            }

            try {
                userSession.setUserInfo(userRepository.fetchUserInfo())
                _isSignedIn.value = true
            } catch (e: Exception) {
                Log.e("SplashViewModel", "Error fetching user info", e)
                tokenManager.clearTokens()
            } finally {
                _isCheckCompleted.value = true
            }
        }
    }
}