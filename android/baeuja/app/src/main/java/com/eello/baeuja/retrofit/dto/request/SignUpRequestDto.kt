package com.eello.baeuja.retrofit.dto.request

import android.icu.util.TimeZone
import java.util.Locale

data class SignUpRequestDto(
    val email: String? = null,
    val nickname: String? = null,
    val language: String = Locale.getDefault().language,
    val timezone: String = TimeZone.getDefault().id,
    val loginType: SignUpType
) {
    enum class SignUpType {
        GUEST,
        GOOGLE,
    }
}