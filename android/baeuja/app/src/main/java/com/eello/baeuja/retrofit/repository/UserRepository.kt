package com.eello.baeuja.retrofit.repository

import com.eello.baeuja.retrofit.api.UserAPI
import com.eello.baeuja.retrofit.core.apiCall
import com.eello.baeuja.retrofit.core.handle
import com.eello.baeuja.viewmodel.UserInfo
import javax.inject.Inject

interface UserRepository {
    suspend fun fetchUserInfo(): UserInfo
}

class UserRepositoryImpl @Inject constructor(
    private val userAPI: UserAPI,
) : UserRepository {

    override suspend fun fetchUserInfo(): UserInfo {
        val apiResult = apiCall { userAPI.fetchUserInfo() }
        return apiResult.handle(
            onSuccess = {
                it.data?.toUserInfo() ?: throw Exception("User data is null")
            },
            onFailure = {
                throw Exception("Unauthorized")
            }
        )
    }
}