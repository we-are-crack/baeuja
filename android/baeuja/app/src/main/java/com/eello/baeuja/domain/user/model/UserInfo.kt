package com.eello.baeuja.domain.user.model

import java.time.ZoneId
import java.util.Locale

data class UserInfo(
    val email: String?,
    val displayName: String,
    val language: Locale,
    val timezone: ZoneId,
    val signInType: SignInType
)
