package com.eello.baeuja.domain.user.repository

import com.eello.baeuja.domain.user.model.UserInfo


interface UserRepository {

    suspend fun fetchUserInfo(): UserInfo
}