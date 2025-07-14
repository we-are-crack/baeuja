package com.eello.baeuja.auth

sealed class AuthResult {
    object Idle : AuthResult()
    object Success : AuthResult()
    object Unregistered : AuthResult()
    object Failure : AuthResult()
}