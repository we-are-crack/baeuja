package com.eello.baeuja.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eello.baeuja.auth.AuthResult
import com.eello.baeuja.retrofit.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DisplayNameAvailable {
    object Idle : DisplayNameAvailable()
    object Available : DisplayNameAvailable()
    data class Unavailable(val reason: String) : DisplayNameAvailable()
}

@OptIn(FlowPreview::class)
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _displayName: MutableStateFlow<String> = MutableStateFlow("")
    val displayName: StateFlow<String> = _displayName

    private val _isAvailable: MutableStateFlow<DisplayNameAvailable> =
        MutableStateFlow(DisplayNameAvailable.Idle)
    val isAvailable: StateFlow<DisplayNameAvailable> = _isAvailable

    private val _signInResult: MutableStateFlow<AuthResult> = MutableStateFlow(AuthResult.Idle)
    val signInResult: StateFlow<AuthResult> = _signInResult

    init {
        viewModelScope.launch {
            // @OptIn(FlowPreview::class)
            _displayName.debounce(500).collect { checkDisplayNameAvailability(it) }
        }
    }

    fun signUp(googleSignInUserInfo: GoogleSignInUserInfo) {
        viewModelScope.launch {
            try {
                _signInResult.value =
                    authRepository.googleSignUp(googleSignInUserInfo, _displayName.value)
            } catch (e: Exception) {
                Log.e("SignUpViewModel", "Error during Google sign-up", e)
                _signInResult.value = AuthResult.Failure
            }
        }
    }

    fun onDisplayNameChanged(newDisplayName: String) {
        _displayName.value = newDisplayName
    }

    private suspend fun checkDisplayNameAvailability(displayName: String) {
        if (displayName.isBlank()) {
            _isAvailable.value = DisplayNameAvailable.Unavailable("non blank")
            return
        }

        try {
            val isAvailable = authRepository.checkDisplayNameAvailable(displayName)
            _isAvailable.value = isAvailable
        } catch (e: Exception) {
            _isAvailable.value = DisplayNameAvailable.Unavailable("network error")
        }
    }
}