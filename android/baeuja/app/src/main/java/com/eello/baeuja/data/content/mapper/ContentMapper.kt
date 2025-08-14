package com.eello.baeuja.data.content.mapper

import com.eello.baeuja.data.content.dto.response.NewContentResponse
import com.eello.baeuja.data.content.dto.response.WordContentResponse
import com.eello.baeuja.domain.content.model.ContentMeta
import com.eello.baeuja.domain.content.model.SentenceUnit
import com.eello.baeuja.domain.content.model.WordContent

fun NewContentResponse.toDomainModel() = ContentMeta(
    classification = classification,
    title = title,
    artist = artist,
    director = director,
    thumbnailUrl = thumbnailUrl,
    unitCount = unitCount,
    wordCount = wordCount
)

fun WordContentResponse.toDomainModel() = WordContent(
    wordId = wordId,
    koreanWord = koreanWord,
    importance = importance,
    sentences = sentences.map { it.toDomainModel() }
)

fun WordContentResponse.SentenceUnitResponse.toDomainModel() = SentenceUnit(
    unitId = unitId,
    sentenceId = sentenceId,
    koreanSentence = koreanSentence,
    koreanWordInSentence = koreanWordInSentence,
    englishSentence = englishSentence,
    englishWordInSentence = englishWordInSentence,
    unitThumbnailUrl = unitThumbnailUrl
)