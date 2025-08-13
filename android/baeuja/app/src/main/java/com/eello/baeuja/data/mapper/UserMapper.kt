package com.eello.baeuja.data.mapper

import com.eello.baeuja.data.dto.response.UserInfoResponse
import com.eello.baeuja.domain.model.user.UserInfo
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