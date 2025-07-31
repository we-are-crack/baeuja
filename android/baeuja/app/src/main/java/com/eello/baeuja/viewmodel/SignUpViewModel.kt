package com.eello.baeuja.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eello.baeuja.auth.AuthResult
import com.eello.baeuja.retrofit.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import timber.log.Timber
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
            _displayName.debounce(500)
                .drop(1)
                .collect {
                    if (validateDisplayNameFormat(it)) {
                        checkDisplayNameAvailability(it)
                    } else {
                        _isAvailable.value =
                            DisplayNameAvailable.Unavailable("Use 2–20 letters or numbers.")
                    }
                }
        }
    }

    companion object {
        private val DISPLAY_NAME_REGEX = Regex("^[a-zA-Z0-9가-힣]{2,20}\$")
    }

    fun signUp(googleSignInUserInfo: GoogleSignInUserInfo) {
        viewModelScope.launch {
            try {
                _signInResult.value =
                    authRepository.googleSignUp(googleSignInUserInfo, _displayName.value)
            } catch (e: Exception) {
                Timber.e(e, "구글 로그인 회원 등록 실패")
                _signInResult.value = AuthResult.Failure(message = e.message)
            }
        }
    }

    fun onDisplayNameChanged(newDisplayName: String) {
        _displayName.value = newDisplayName
    }

    private fun validateDisplayNameFormat(displayName: String): Boolean {
        return DISPLAY_NAME_REGEX.matchEntire(displayName) != null
    }

    private suspend fun checkDisplayNameAvailability(displayName: String) {
        try {
            val isAvailable = authRepository.checkDisplayNameAvailable(displayName)
            _isAvailable.value = isAvailable
        } catch (e: Exception) {
            _isAvailable.value = DisplayNameAvailable.Unavailable("network error")
        }
    }
}