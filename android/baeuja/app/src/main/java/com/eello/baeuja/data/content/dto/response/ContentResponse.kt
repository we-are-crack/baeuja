package com.eello.baeuja.data.content.dto.response

import com.eello.baeuja.domain.content.model.Classification


data class ContentResponse(
    val id: Long,
    val classification: Classification,
    val title: String,
    val thumbnailUrl: String,
    val progressRate: Int,
    val artist: String? = null,
    val director: String? = null
)