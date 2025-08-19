package com.eello.baeuja.ui.screen.learning.unit

import com.eello.baeuja.domain.content.model.SentenceType

sealed class ContentUnitUiModel(
    open val unitId: Long,
    open val thumbnailUrl: String,
    open val lastLearnedDate: String?
) {
    data class PopUnitUiModel(
        override val unitId: Long,
        override val thumbnailUrl: String,
        override val lastLearnedDate: String?,
        val sentencesCount: Int,
        val wordsCount: Int,
        val progressRate: Float
    ) : ContentUnitUiModel(unitId, thumbnailUrl, lastLearnedDate)

    data class VisualUnitUiModel(
        override val unitId: Long,
        override val thumbnailUrl: String,
        override val lastLearnedDate: String?,
        val korean: String,
        val english: String,
        val isBookmarked: Boolean = false,
        val sentenceType: SentenceType
    ) : ContentUnitUiModel(unitId, thumbnailUrl, lastLearnedDate)
}