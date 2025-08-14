package com.eello.baeuja.data.content.repository

import com.eello.baeuja.data.common.pagination.toDomainModel
import com.eello.baeuja.data.content.mapper.toDomainModel
import com.eello.baeuja.data.network.ApiCaller
import com.eello.baeuja.data.network.ApiCallerHolder
import com.eello.baeuja.data.network.callApi
import com.eello.baeuja.domain.common.pagination.model.Pagination
import com.eello.baeuja.domain.content.model.Classification
import com.eello.baeuja.domain.content.model.ContentMeta
import com.eello.baeuja.domain.content.model.WordContent
import com.eello.baeuja.domain.content.repository.ContentRepository
import com.eello.baeuja.exception.AppError
import com.eello.baeuja.exception.AppException
import com.eello.baeuja.retrofit.api.ContentAPI
import com.eello.baeuja.retrofit.core.handle
import timber.log.Timber
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(
    override val apiCaller: ApiCaller,
    private val contentAPI: ContentAPI
) : ContentRepository, ApiCallerHolder {

    override suspend fun getNewContents(): List<ContentMeta> {
        Timber.d("서버로 부터 새로운 콘텐츠 로드...")
        val apiResult = callApi { contentAPI.fetchHomeNewContents() }
        return apiResult.handle(
            onSuccess = { body ->
                body.data?.map { it.toDomainModel() }?.toList()
                    ?: throw AppException(AppError.Api.EmptyResponse())
            },
            onFailure = { reason ->
                throw AppException(reason)
            }
        )
    }

    override suspend fun getWordContents(excludeWords: List<Int>): List<WordContent> {
        Timber.d("서버로 부터 학습 콘텐츠 로드...")
        val apiResult = callApi { contentAPI.fetchHomeLearningContents(excludeWords) }
        return apiResult.handle(
            onSuccess = { body ->
                body.data?.map { it.toDomainModel() }?.toList()
                    ?: throw AppException(AppError.Api.EmptyResponse())
            },
            onFailure = { reason ->
                throw AppException(reason)
            }
        )
    }

    override suspend fun getMainContents(size: Int): Map<Classification, List<ContentMeta>> {
        Timber.d("서버로부터 학습 메인 콘텐츠 로드...")
        val apiResult = callApi { contentAPI.fetchMainContents(size) }

        return apiResult.handle(
            onSuccess = { body ->
                body.data?.map { (key, value) ->
                    key to value.map { it.toDomainModel() }
                }?.toMap() ?: throw AppException(AppError.Api.EmptyResponse())
            },
            onFailure = { reason ->
                throw AppException(reason)
            }
        )
    }

    override suspend fun getContentsByClassification(
        classification: Classification,
        page: Int,
        size: Int
    ): Pagination<ContentMeta> {
        val apiResult = apiCaller.call {
            contentAPI.fetchContentsByClassification(classification, page, size)
        }

        return apiResult.handle(
            onSuccess = { body ->
                body.data?.toDomainModel { it.toDomainModel() }
                    ?: throw AppException(AppError.Api.EmptyResponse())
            },
            onFailure = { reason ->
                throw AppException(reason)
            }
        )
    }

    override suspend fun getContentDetail(id: Long): ContentMeta {
        val apiResult = apiCaller.call { contentAPI.fetchContentDetail(id) }
        return apiResult.handle(
            onSuccess = { body ->
                body.data?.toDomainModel()
                    ?: throw AppException(AppError.Api.EmptyResponse())
            },
            onFailure = { reason ->
                throw AppException(reason)
            }
        )
    }
}