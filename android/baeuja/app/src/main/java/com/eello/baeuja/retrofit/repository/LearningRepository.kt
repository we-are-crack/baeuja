package com.eello.baeuja.retrofit.repository

import com.eello.baeuja.exception.AppError
import com.eello.baeuja.exception.AppException
import com.eello.baeuja.retrofit.api.LearningAPI
import com.eello.baeuja.retrofit.core.ApiCaller
import com.eello.baeuja.retrofit.core.handle
import com.eello.baeuja.viewmodel.ContentClassification
import com.eello.baeuja.viewmodel.LearningItem
import timber.log.Timber
import javax.inject.Inject

interface LearningRepository {

    suspend fun fetchMainContents(size: Int = 5): Map<ContentClassification, List<LearningItem>>
}


class LearningRepositoryImpl @Inject constructor(
    private val apiCaller: ApiCaller,
    private val learningAPI: LearningAPI
) : LearningRepository {

    override suspend fun fetchMainContents(size: Int): Map<ContentClassification, List<LearningItem>> {
        Timber.d("서버로부터 학습 메인 콘텐츠 로드...")
        val apiResult = apiCaller.call {
            learningAPI.fetchMainContents(size)
        }

        return apiResult.handle(
            onSuccess = { body ->
                body.data?.map { (key, value) ->
                    key to value.map { it.toLearningItem() }
                }?.toMap() ?: throw AppException(AppError.Api.EmptyResponse())
            },
            onFailure = { reason ->
                throw AppException(reason)
            }
        )
    }
}