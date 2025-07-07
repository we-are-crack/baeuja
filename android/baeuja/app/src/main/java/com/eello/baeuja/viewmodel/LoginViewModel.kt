package com.eello.baeuja.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class UserInfo(
    val email: String?,
    val googleId: String?,
    val displayName: String?
)

class LoginViewModel : ViewModel() {
    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    fun onGoogleSignInSuccess(account: GoogleSignInAccount) {
        _userInfo.value = UserInfo(
            email = account.email,
            googleId = account.id,
            displayName = null
        )

        Log.i("UserInfo", "Logged User: ${_userInfo.value}")
    }

    fun onInputDisplayName(displayName: String) {
        _userInfo.value = _userInfo.value?.copy(displayName = displayName)
    }
}