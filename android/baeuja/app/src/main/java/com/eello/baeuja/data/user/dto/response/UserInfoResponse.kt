package com.eello.baeuja.data.user.dto.response

import com.eello.baeuja.domain.user.model.SignInType


data class UserInfoResponse(
    val email: String,
    val nickname: String,
    val language: String,
    val timezone: String,
    val loginType: SignInType
)