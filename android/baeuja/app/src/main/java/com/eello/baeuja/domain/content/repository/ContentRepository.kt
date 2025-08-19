package com.eello.baeuja.domain.content.repository

import com.eello.baeuja.domain.common.pagination.model.Pagination
import com.eello.baeuja.domain.content.model.Classification
import com.eello.baeuja.domain.content.model.ContentMeta
import com.eello.baeuja.domain.content.model.ContentUnit
import com.eello.baeuja.domain.content.model.WordContent

interface ContentRepository {

    suspend fun getNewContents(): List<ContentMeta>
    suspend fun getWordContents(excludeWords: List<Int> = emptyList()): List<WordContent>
    suspend fun getMainContents(size: Int = 5): Map<Classification, List<ContentMeta>>
    suspend fun getContentsByClassification(
        classification: Classification,
        page: Int,
        size: Int = 5
    ): Pagination<ContentMeta>

    suspend fun getContentDetail(id: Long): ContentMeta

    suspend fun getContentUnits(contentId: Long, classification: Classification): List<ContentUnit>
}