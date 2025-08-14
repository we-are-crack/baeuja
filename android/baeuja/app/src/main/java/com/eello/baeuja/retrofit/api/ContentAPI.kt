package com.eello.baeuja.retrofit.api

import com.eello.baeuja.data.common.pagination.PaginationResponse
import com.eello.baeuja.data.content.dto.response.ContentDetailResponse
import com.eello.baeuja.data.content.dto.response.ContentResponse
import com.eello.baeuja.data.content.dto.response.NewContentResponse
import com.eello.baeuja.data.content.dto.response.WordContentResponse
import com.eello.baeuja.domain.content.model.Classification
import com.eello.baeuja.retrofit.core.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ContentAPI {

    /**
     * Home 화면 관련 API
     */
    @GET("home/contents")
    suspend fun fetchHomeNewContents(): Response<ApiResponse<List<NewContentResponse>>>

    @GET("home/words")
    suspend fun fetchHomeLearningContents(
        @Query("excludeIds") excludeWordIds: List<Int> = emptyList()
    ): Response<ApiResponse<List<WordContentResponse>>>

    /**
     * Learning 화면 관련 API
     */
    @GET("learning/contents/all")
    suspend fun fetchMainContents(
        @Query("size") size: Int
    ): Response<ApiResponse<Map<Classification, List<ContentResponse>>>>

    @GET("learning/contents")
    suspend fun fetchContentsByClassification(
        @Query("classification") classification: Classification,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ApiResponse<PaginationResponse<ContentResponse>>>

    @GET("learning/contents/{contentId}")
    suspend fun fetchContentDetail(
        @Path("contentId") contentId: Long
    ): Response<ApiResponse<ContentDetailResponse>>
}