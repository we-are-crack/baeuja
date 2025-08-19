package com.eello.baeuja.domain.content.model

import java.time.LocalDate

sealed class ContentUnit(
    open val unitId: Long,
    open val thumbnailUrl: String,
    open val lastLearnedDate: LocalDate?
) {
    data class PopUnit(
        override val unitId: Long,
        override val thumbnailUrl: String,
        override val lastLearnedDate: LocalDate?,
        val sentencesCount: Int,
        val wordsCount: Int,
        val progressRate: Float
    ) : ContentUnit(unitId, thumbnailUrl, lastLearnedDate)

    data class VisualUnit(
        override val unitId: Long,
        override val thumbnailUrl: String,
        override val lastLearnedDate: LocalDate?,
        val korean: String,
        val english: String,
        val isBookmarked: Boolean,
        val sentenceType: SentenceType
    ) : ContentUnit(unitId, thumbnailUrl, lastLearnedDate)
}