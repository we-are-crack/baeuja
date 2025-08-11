package com.eello.baeuja.retrofit.api

import com.eello.baeuja.retrofit.core.ApiResponse
import com.eello.baeuja.retrofit.dto.response.LearningMainContentsData
import com.eello.baeuja.viewmodel.ContentClassification
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LearningAPI {

    @GET("learning/contents")
    suspend fun fetchMainContents(
        @Query("size") size: Int
    ): Response<ApiResponse<Map<ContentClassification, List<LearningMainContentsData>>>>
}