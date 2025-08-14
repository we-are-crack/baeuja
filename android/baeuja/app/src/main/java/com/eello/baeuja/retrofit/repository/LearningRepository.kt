package com.eello.baeuja.retrofit.repository

import com.eello.baeuja.data.network.ApiCaller
import com.eello.baeuja.exception.AppError
import com.eello.baeuja.exception.AppException
import com.eello.baeuja.retrofit.api.LearningAPI
import com.eello.baeuja.retrofit.core.handle
import com.eello.baeuja.retrofit.dto.response.Page
import com.eello.baeuja.viewmodel.ContentClassification
import com.eello.baeuja.viewmodel.LearningItem
import com.eello.baeuja.viewmodel.LearningItemDetail
import timber.log.Timber
import javax.inject.Inject

interface LearningRepository {

    suspend fun fetchMainContents(size: Int = 5): Map<ContentClassification, List<LearningItem>>

    suspend fun fetchContentsByClassification(
        classification: ContentClassification,
        page: Int,
        size: Int = 5
    ): Page<LearningItem>

    suspend fun fetchContentDetailInfo(id: Long): LearningItemDetail
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

    override suspend fun fetchContentsByClassification(
        classification: ContentClassification,
        page: Int,
        size: Int
    ): Page<LearningItem> {
        val apiResult = apiCaller.call {
            learningAPI.fetchContentsByClassification(classification, page, size)
        }

        return apiResult.handle(
            onSuccess = { body ->
                body.data?.let {
                    Page.of(
                        content = it.content.map { it.toLearningItem() },
                        pageInfo = it.pageInfo
                    )
                } ?: throw AppException(AppError.Api.EmptyResponse())
            },
            onFailure = { reason ->
                throw AppException(reason)
            }
        )
    }

    override suspend fun fetchContentDetailInfo(id: Long): LearningItemDetail {
        val apiResult = apiCaller.call { learningAPI.fetchContentDetailInfo(id) }
        return apiResult.handle(
            onSuccess = { body ->
                body.data?.toLearningItemDetail()
                    ?: throw AppException(AppError.Api.EmptyResponse())
            },
            onFailure = { reason ->
                throw AppException(reason)
            }
        )
    }
}