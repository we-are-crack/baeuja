package com.eello.baeuja.domain.auth.model

sealed class AuthResult {
    object Idle : AuthResult()
    object Success : AuthResult()
    object Unregistered : AuthResult()
    data class Failure(val message: String? = null) : AuthResult()
}