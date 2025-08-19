package com.eello.baeuja.data.content.dto.response

import com.eello.baeuja.domain.content.model.Classification

data class NewContentResponse(
    val classification: Classification,
    val id: Long,
    val title: String,
    val artist: String? = null,
    val director: String? = null,
    val thumbnailUrl: String,
    val unitCount: Int,
    val wordCount: Int
)