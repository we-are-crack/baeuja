package com.eello.baeuja.ui.screen.learning.main

import com.eello.baeuja.domain.content.model.Classification

data class LearningContentUiModel(
    val id: Long,
    val classification: Classification,
    val title: String,
    val artist: String? = null,
    val director: String? = null,
    val progressRate: Int,
    val thumbnailUrl: String
)