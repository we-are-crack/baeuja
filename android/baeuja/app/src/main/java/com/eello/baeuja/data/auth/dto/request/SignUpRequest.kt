package com.eello.baeuja.data.auth.dto.request

import com.eello.baeuja.domain.user.model.SignInType
import java.time.ZoneId
import java.util.Locale

data class SignUpRequest(
    val email: String? = null,
    val nickname: String? = null,
    val language: String = Locale.getDefault().language,
    val timezone: String = ZoneId.systemDefault().id,
    val loginType: SignInType
)