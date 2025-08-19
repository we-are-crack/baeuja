package com.eello.baeuja.data.content.mapper

import com.eello.baeuja.data.content.dto.response.ContentUnitResponse
import com.eello.baeuja.domain.content.model.ContentUnit
import com.eello.baeuja.domain.content.model.SentenceType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun ContentUnitResponse.toDomainModel(): ContentUnit {
    return if (sentence == null) {
        ContentUnit.PopUnit(
            unitId = id,
            thumbnailUrl = thumbnail,
            lastLearnedDate = if (lastLearned.isBlank()) null else LocalDate.parse(
                lastLearned,
                localDateFormatter
            ),
            sentencesCount = sentencesCount,
            wordsCount = wordsCount,
            progressRate = progressRate.toFloat() / 100
        )
    } else {
        ContentUnit.VisualUnit(
            unitId = id,
            thumbnailUrl = thumbnail,
            lastLearnedDate = if (lastLearned.isBlank()) null else LocalDate.parse(
                lastLearned,
                localDateFormatter
            ),
            korean = sentence.korean,
            english = sentence.english,
            isBookmarked = sentence.isBookmark,
            sentenceType = if (sentence.isConversation) {
                SentenceType.CONVERSATION
            } else {
                SentenceType.FAMOUS_LINE
            }
        )
    }
}