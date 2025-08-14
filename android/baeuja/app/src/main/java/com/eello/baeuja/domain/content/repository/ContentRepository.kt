package com.eello.baeuja.domain.content.repository

import com.eello.baeuja.domain.content.model.ContentMeta
import com.eello.baeuja.domain.content.model.WordContent

interface ContentRepository {

    suspend fun getNewContents(): List<ContentMeta>
    suspend fun getWordContents(excludeWords: List<Int> = emptyList()): List<WordContent>
}