package com.eello.baeuja.retrofit.repository

import com.eello.baeuja.exception.AppError
import com.eello.baeuja.exception.AppException
import com.eello.baeuja.retrofit.api.ContentAPI
import com.eello.baeuja.retrofit.core.ApiCaller
import com.eello.baeuja.retrofit.core.handle
import com.eello.baeuja.viewmodel.HomeLearningContent
import com.eello.baeuja.viewmodel.NewContentItem
import timber.log.Timber
import javax.inject.Inject

interface ContentRepository {

    suspend fun fetchHomeNewContents(): List<NewContentItem>
    suspend fun fetchHomeLearningContents(excludeWords: List<Int> = emptyList()): List<HomeLearningContent>
}

class ContentRepositoryImpl @Inject constructor(
    private val apiCaller: ApiCaller,
    private val contentAPI: ContentAPI
) : ContentRepository {

    override suspend fun fetchHomeNewContents(): List<NewContentItem> {
        Timber.d("서버로 부터 새로운 콘텐츠 로드...")
        val apiResult = apiCaller.call { contentAPI.fetchHomeNewContents() }
        return apiResult.handle(
            onSuccess = { body ->
                body.data?.map { it.toHomeNewContent() }?.toList()
                    ?: throw AppException(AppError.Api.EmptyResponse())
            },
            onFailure = { reason ->
                throw AppException(reason)
            }
        )
    }

    override suspend fun fetchHomeLearningContents(excludeWords: List<Int>): List<HomeLearningContent> {
        Timber.d("서버로 부터 학습 콘텐츠 로드...")
        val apiResult = apiCaller.call { contentAPI.fetchHomeLearningContents(excludeWords) }
        return apiResult.handle(
            onSuccess = { body ->
                body.data?.map { it.toHomeLearningContent() }?.toList()
                    ?: throw AppException(AppError.Api.EmptyResponse())
            },
            onFailure = { reason ->
                throw AppException(reason)
            }
        )
    }
}
