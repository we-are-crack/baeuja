package com.eello.baeuja.data.auth.dto.request

import com.eello.baeuja.viewmodel.GoogleSignInUserInfo

data class SignInRequest(val email: String) {

    companion object {
        fun from(googleSignInUserInfo: GoogleSignInUserInfo): SignInRequest {
            val email = googleSignInUserInfo.email
                ?: throw IllegalArgumentException("Email is null")
            return SignInRequest(email)
        }
    }
}