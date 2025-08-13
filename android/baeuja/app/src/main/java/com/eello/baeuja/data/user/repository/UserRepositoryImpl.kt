package com.eello.baeuja.data.user.repository

import com.eello.baeuja.data.user.mapper.toDomain
import com.eello.baeuja.domain.user.model.UserInfo
import com.eello.baeuja.domain.user.repository.UserRepository
import com.eello.baeuja.exception.AppError
import com.eello.baeuja.exception.AppException
import com.eello.baeuja.retrofit.api.UserAPI
import com.eello.baeuja.retrofit.core.ApiCaller
import com.eello.baeuja.retrofit.core.handle
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiCaller: ApiCaller,
    private val userAPI: UserAPI,
) : UserRepository {

    override suspend fun fetchUserInfo(): UserInfo {
        val apiResult = apiCaller.call { userAPI.fetchUserInfo() }
        return apiResult.handle(
            onSuccess = { body ->
                body.data?.toDomain() ?: throw AppException(AppError.Api.EmptyResponse())
            },
            onFailure = { reason ->
                throw AppException(reason)
            }
        )
    }
}