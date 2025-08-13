package com.eello.baeuja.domain.auth.repository

import com.eello.baeuja.domain.auth.model.AuthResult
import com.eello.baeuja.viewmodel.DisplayNameAvailable
import com.eello.baeuja.viewmodel.GoogleSignInUserInfo

interface AuthRepository {

    suspend fun signIn(googleSignInUserInfo: GoogleSignInUserInfo): AuthResult
    suspend fun guestSignUp(): AuthResult
    suspend fun googleSignUp(
        googleSignInUserInfo: GoogleSignInUserInfo,
        displayName: String
    ): AuthResult

    suspend fun checkDisplayNameAvailable(displayName: String): DisplayNameAvailable
}