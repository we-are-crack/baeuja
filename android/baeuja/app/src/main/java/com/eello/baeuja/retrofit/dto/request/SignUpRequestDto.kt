package com.eello.baeuja.retrofit.dto.request

import com.eello.baeuja.domain.model.user.SignInType
import java.time.ZoneId
import java.util.Locale

data class SignUpRequestDto(
    val email: String? = null,
    val nickname: String? = null,
    val language: String = Locale.getDefault().language,
    val timezone: String = ZoneId.systemDefault().id,
    val loginType: SignInType
)