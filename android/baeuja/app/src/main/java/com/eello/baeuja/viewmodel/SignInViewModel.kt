package com.eello.baeuja.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eello.baeuja.auth.AuthResult
import com.eello.baeuja.retrofit.repository.AuthRepository
import com.eello.baeuja.retrofit.repository.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GoogleSignInUserInfo(
    val email: String?,
    val googleId: String?,
)

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val userSession: UserSession
) : ViewModel() {
    private val _googleSignInUserInfo = MutableStateFlow<GoogleSignInUserInfo?>(null)
    val googleSignInUserInfo: StateFlow<GoogleSignInUserInfo?> = _googleSignInUserInfo

    private val _signInResult = MutableStateFlow<AuthResult>(AuthResult.Idle)
    val signInResult: StateFlow<AuthResult> = _signInResult

    fun onGoogleSignInSuccess(account: GoogleSignInAccount) {
        _googleSignInUserInfo.value = GoogleSignInUserInfo(
            email = account.email,
            googleId = account.id,
        )

        Log.i("UserInfo", "Logged User: ${_googleSignInUserInfo.value}")
    }

    fun googleSignIn() {
        viewModelScope.launch {
            val gui = _googleSignInUserInfo.value
            if (gui == null) {
                Log.e("LoginViewModel", "GoogleSignInUserInfo is null")
                return@launch
            }

            try {
                val signInResult = authRepository.signIn(gui)
                if (signInResult == AuthResult.Success) {
                    userSession.setUserInfo(userRepository.fetchUserInfo())
                }
                _signInResult.value = signInResult
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error during Google sign-in", e)
                _signInResult.value = AuthResult.Failure()
            }
        }
    }

    fun guestSignIn() {
        viewModelScope.launch {
            try {
                _signInResult.value = authRepository.guestSignUp()
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error guest Google sign-in", e)
                _signInResult.value = AuthResult.Failure()
            }
        }
    }
}