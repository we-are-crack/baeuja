package com.eello.baeuja.retrofit.api

import com.eello.baeuja.retrofit.core.ApiResponse
import com.eello.baeuja.retrofit.dto.response.HomeLearningContentsData
import com.eello.baeuja.retrofit.dto.response.HomeNewContentsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ContentAPI {

    @GET("home/contents")
    suspend fun fetchHomeNewContents(): Response<ApiResponse<List<HomeNewContentsData>>>

    @GET("home/words")
    suspend fun fetchHomeLearningContents(
        @Query("excludeWords") excludeWordIds: List<Int> = emptyList()
    ): Response<ApiResponse<List<HomeLearningContentsData>>>
}