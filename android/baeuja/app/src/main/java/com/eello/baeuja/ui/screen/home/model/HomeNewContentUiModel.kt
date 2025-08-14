package com.eello.baeuja.ui.screen.home.model

import com.eello.baeuja.domain.content.model.Classification

data class HomeNewContentUiModel(
    val id: Long,
    val classification: Classification,
    val title: String,
    val artist: String? = null,
    val director: String? = null,
    val thumbnailUrl: String,
    val unitCount: Int,
    val wordCount: Int
)