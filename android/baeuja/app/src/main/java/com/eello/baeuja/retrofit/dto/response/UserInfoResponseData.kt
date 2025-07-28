package com.eello.baeuja.retrofit.dto.response

import com.eello.baeuja.session.SignInType
import com.eello.baeuja.session.UserInfo
import java.time.ZoneId
import java.util.Locale

data class UserInfoResponseData(
    val email: String,
    val nickname: String,
    val language: String,
    val timezone: String,
    val loginType: SignInType
) {
    fun toUserInfo(): UserInfo = UserInfo(
        email = email,
        displayName = nickname,
        language = Locale.forLanguageTag(language),
        timezone = ZoneId.of(timezone),
        loginType = loginType
    )
}