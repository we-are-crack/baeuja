package com.eello.baeuja.retrofit.repository

import com.eello.baeuja.exception.AppError
import com.eello.baeuja.exception.AppException
import com.eello.baeuja.retrofit.api.UserAPI
import com.eello.baeuja.retrofit.core.ApiCaller
import com.eello.baeuja.retrofit.core.handle
import com.eello.baeuja.session.UserInfo
import javax.inject.Inject

interface UserRepository {
    suspend fun fetchUserInfo(): UserInfo
}

class UserRepositoryImpl @Inject constructor(
    private val apiCaller: ApiCaller,
    private val userAPI: UserAPI,
) : UserRepository {

    override suspend fun fetchUserInfo(): UserInfo {
        val apiResult = apiCaller.call { userAPI.fetchUserInfo() }
        return apiResult.handle(
            onSuccess = { body ->
                body.data?.toUserInfo() ?: throw AppException(AppError.Api.EmptyResponse())
            },
            onFailure = { reason ->
                throw AppException(reason)
            }
        )
    }
}