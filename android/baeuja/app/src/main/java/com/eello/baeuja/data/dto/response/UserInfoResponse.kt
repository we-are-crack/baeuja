package com.eello.baeuja.data.dto.response

import com.eello.baeuja.domain.model.user.SignInType


data class UserInfoResponse(
    val email: String,
    val nickname: String,
    val language: String,
    val timezone: String,
    val loginType: SignInType
)