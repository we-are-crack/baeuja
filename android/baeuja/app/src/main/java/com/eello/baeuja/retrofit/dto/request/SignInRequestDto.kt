package com.eello.baeuja.retrofit.dto.request

import com.eello.baeuja.viewmodel.GoogleSignInUserInfo

data class SignInRequestDto(val email: String) {

    companion object {
        fun from(googleSignInUserInfo: GoogleSignInUserInfo): SignInRequestDto {
            val email = googleSignInUserInfo.email
                ?: throw IllegalArgumentException("Email is null")
            return SignInRequestDto(email)
        }
    }
}