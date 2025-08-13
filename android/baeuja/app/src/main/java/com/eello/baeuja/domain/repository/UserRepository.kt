package com.eello.baeuja.domain.repository

import com.eello.baeuja.domain.model.user.UserInfo


interface UserRepository {

    suspend fun fetchUserInfo(): UserInfo
}