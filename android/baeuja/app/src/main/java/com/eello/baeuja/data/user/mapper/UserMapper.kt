package com.eello.baeuja.data.user.mapper

import com.eello.baeuja.data.user.dto.response.UserInfoResponse
import com.eello.baeuja.domain.user.model.UserInfo
import java.time.ZoneId
import java.util.Locale

/**
 * UserInfo <-> UserInfoResponse
 */
fun UserInfoResponse.toDomain() = UserInfo(
    email = email,
    displayName = nickname,
    language = Locale.forLanguageTag(language),
    timezone = ZoneId.of(timezone),
    signInType = loginType
)