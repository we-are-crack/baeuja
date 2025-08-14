package com.eello.baeuja.domain.content.model

data class ContentMeta(
    val id: Long? = null, // 후에 non-nullable 로 변경
    val classification: Classification,
    val title: String,
    val artist: String? = null,
    val director: String? = null,
    val description: String? = null,
    val thumbnailUrl: String,
    val youtubeId: String? = null,
    val progressRate: Int? = null,
    val unitCount: Int? = null,
    val wordCount: Int? = null
)
