package com.eello.baeuja.retrofit.api

import com.eello.baeuja.data.content.dto.response.NewContentResponse
import com.eello.baeuja.data.content.dto.response.WordContentResponse
import com.eello.baeuja.retrofit.core.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ContentAPI {

    @GET("home/contents")
    suspend fun fetchHomeNewContents(): Response<ApiResponse<List<NewContentResponse>>>

    @GET("home/words")
    suspend fun fetchHomeLearningContents(
        @Query("excludeIds") excludeWordIds: List<Int> = emptyList()
    ): Response<ApiResponse<List<WordContentResponse>>>
}