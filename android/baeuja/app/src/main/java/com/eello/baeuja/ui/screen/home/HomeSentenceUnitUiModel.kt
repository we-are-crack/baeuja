package com.eello.baeuja.ui.screen.home

data class HomeSentenceUnitUiModel(
    val unitId: Int,
    val sentenceId: Int,
    val koreanSentence: String,
    val koreanWordInSentence: String,
    val englishSentence: String,
    val englishWordInSentence: String,
    val unitThumbnailUrl: String
)