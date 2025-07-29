package com.eello.baeuja.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eello.baeuja.auth.AuthResult
import com.eello.baeuja.retrofit.repository.AuthRepository
import com.eello.baeuja.retrofit.repository.UserRepository
import com.eello.baeuja.session.HomeContentSession
import com.eello.baeuja.session.UserSession
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class GoogleSignInUserInfo(
    val email: String?,
    val googleId: String?,
)

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val userSession: UserSession,
    private val homeContentSession: HomeContentSession,

    ) : ViewModel() {
    private val _googleSignInUserInfo = MutableStateFlow<GoogleSignInUserInfo?>(null)
    val googleSignInUserInfo: StateFlow<GoogleSignInUserInfo?> = _googleSignInUserInfo

    private val _signInResult = MutableStateFlow<AuthResult>(AuthResult.Idle)
    val signInResult: StateFlow<AuthResult> = _signInResult

    private val _isHomeContentLoaded = MutableStateFlow(false)
    val isHomeContentLoaded: StateFlow<Boolean> = _isHomeContentLoaded

    fun onGoogleSignInSuccess(account: GoogleSignInAccount) {
        _googleSignInUserInfo.value = GoogleSignInUserInfo(
            email = account.email,
            googleId = account.id,
        )

        Timber.d("Google OAuth 로그인 성공: ${_googleSignInUserInfo.value}")

        googleSignIn()
    }

    fun googleSignIn() {
        Timber.d("Google 로그인 정보로 서비스 로그인 시도...")
        viewModelScope.launch {
            val gui = _googleSignInUserInfo.value
            if (gui == null) {
                Timber.e(
                    "서비스 로그인 실패: Google 로그인 정보가 없음" +
                            "\t`googleSignIn()` 은 `onGoogleSignInSuccess()` 에서만 호출되므로 gui 가 null 이면 안된다."
                )
                return@launch
            }

            try {
                val signInResult = authRepository.signIn(gui)
                if (signInResult == AuthResult.Success) {
                    userSession.setUserInfo(userRepository.fetchUserInfo())
                }
                _signInResult.value = signInResult
            } catch (e: Exception) {
                Timber.e(e, "Google 로그인 정보로 서비스 로그인 시도 중 예외 발생: ${e.message}")
                _signInResult.value = AuthResult.Failure()
            }
        }
    }

    fun guestSignIn() {
        Timber.d("GUEST 로그인 시도...")
        viewModelScope.launch {
            try {
                _signInResult.value = authRepository.guestSignUp()
                Timber.d("GUEST 로그인 성공: signInResult: ${_signInResult.value}")
            } catch (e: Exception) {
                _signInResult.value = AuthResult.Failure()
                Timber.e("GUEST 로그인 실패: ${e.message}, signInResult: ${_signInResult.value}")
            }
        }
    }

    fun loadHomeContent() {
        viewModelScope.launch {
            homeContentSession.initialize()
            _isHomeContentLoaded.value = true
        }
    }
}