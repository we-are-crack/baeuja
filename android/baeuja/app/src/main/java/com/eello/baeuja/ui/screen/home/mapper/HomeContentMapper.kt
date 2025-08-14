package com.eello.baeuja.ui.screen.home.mapper

import com.eello.baeuja.domain.content.model.ContentMeta
import com.eello.baeuja.domain.content.model.SentenceUnit
import com.eello.baeuja.domain.content.model.WordContent
import com.eello.baeuja.ui.screen.home.model.HomeNewContentUiModel
import com.eello.baeuja.ui.screen.home.model.HomeSentenceUnitUiModel
import com.eello.baeuja.ui.screen.home.model.HomeWordContentUiModel

fun ContentMeta.toHomeNewContentUiModel() = HomeNewContentUiModel(
    id = id ?: -1,
    classification = classification,
    title = title,
    artist = artist,
    director = director,
    thumbnailUrl = thumbnailUrl,
    unitCount = unitCount ?: 0,
    wordCount = wordCount ?: 0
)

fun SentenceUnit.toHomeSentenceUnitUiModel() = HomeSentenceUnitUiModel(
    unitId = unitId,
    sentenceId = sentenceId,
    koreanSentence = koreanSentence,
    koreanWordInSentence = koreanWordInSentence,
    englishSentence = englishSentence,
    englishWordInSentence = englishWordInSentence,
    unitThumbnailUrl = unitThumbnailUrl
)

fun WordContent.toHomeWordContentUiModel() = HomeWordContentUiModel(
    wordId = wordId,
    koreanWord = koreanWord,
    importance = importance,
    sentences = sentences.map { it.toHomeSentenceUnitUiModel() }
)