package com.eello.baeuja.retrofit.api

import com.eello.baeuja.retrofit.core.ApiResponse
import com.eello.baeuja.retrofit.dto.response.LearningItemData
import com.eello.baeuja.retrofit.dto.response.LearningItemDetailInfoData
import com.eello.baeuja.retrofit.dto.response.MoreLearningItemsData
import com.eello.baeuja.viewmodel.ContentClassification
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LearningAPI {

    @GET("learning/contents")
    suspend fun fetchMainContents(
        @Query("size") size: Int
    ): Response<ApiResponse<Map<ContentClassification, List<LearningItemData>>>>

    @GET("learning/contents/classification/{classification}")
    suspend fun fetchContentsByClassification(
        @Path("classification") classification: ContentClassification,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ApiResponse<MoreLearningItemsData>>

    @GET("learning/contents/{itemId}")
    suspend fun fetchContentDetailInfo(
        @Path("itemId") itemId: Long
    ): Response<ApiResponse<LearningItemDetailInfoData>>
}