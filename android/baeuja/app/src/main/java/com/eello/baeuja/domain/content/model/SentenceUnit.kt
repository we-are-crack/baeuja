package com.eello.baeuja.domain.content.model

data class SentenceUnit(
    val unitId: Int,
    val sentenceId: Int,
    val koreanSentence: String,
    val koreanWordInSentence: String,
    val englishSentence: String,
    val englishWordInSentence: String,
    val unitThumbnailUrl: String
)