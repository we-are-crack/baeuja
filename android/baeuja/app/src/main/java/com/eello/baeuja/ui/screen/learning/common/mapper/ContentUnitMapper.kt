package com.eello.baeuja.ui.screen.learning.common.mapper

import com.eello.baeuja.domain.content.model.ContentUnit
import com.eello.baeuja.ui.screen.learning.unit.ContentUnitUiModel
import java.time.format.DateTimeFormatter

private val localDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

fun ContentUnit.toUiModel(): ContentUnitUiModel {
    return when (this) {
        is ContentUnit.PopUnit -> toPopUnitUiModel()
        is ContentUnit.VisualUnit -> toVisualUnitUiModel()
    }
}

fun ContentUnit.PopUnit.toPopUnitUiModel() = ContentUnitUiModel.PopUnitUiModel(
    unitId = unitId,
    thumbnailUrl = thumbnailUrl,
    lastLearnedDate = lastLearnedDate?.format(localDateFormatter),
    sentencesCount = sentencesCount,
    wordsCount = wordsCount,
    progressRate = progressRate
)

fun ContentUnit.VisualUnit.toVisualUnitUiModel() = ContentUnitUiModel.VisualUnitUiModel(
    unitId = unitId,
    thumbnailUrl = thumbnailUrl,
    lastLearnedDate = lastLearnedDate?.format(localDateFormatter),
    korean = korean,
    english = english,
    isBookmarked = isBookmarked,
    sentenceType = sentenceType
)