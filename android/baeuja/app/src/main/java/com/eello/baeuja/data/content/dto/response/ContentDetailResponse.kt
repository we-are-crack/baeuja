package com.eello.baeuja.data.content.dto.response

import com.eello.baeuja.domain.content.model.Classification


data class ContentDetailResponse(
    val id: Long,
    val classification: Classification,
    val title: String,
    val artist: String? = null,
    val director: String? = null,
    val thumbnailUrl: String,
    val youtubeId: String,
    val description: String
)