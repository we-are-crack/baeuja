package com.eello.baeuja.ui.screen.home.model

data class HomeWordContentUiModel(
    val wordId: Int,
    val koreanWord: String,
    val importance: String,
    val sentences: List<HomeSentenceUnitUiModel>
)