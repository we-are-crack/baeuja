package com.eello.baeuja.data.content.dto.response

data class ContentUnitResponse(
    val id: Long,
    val thumbnail: String,
    val sentencesCount: Int = 0,
    val wordsCount: Int = 0,
    val progressRate: Int = 0,
    val lastLearned: String,
    val sentence: VisualContentUnitResponse?
)
