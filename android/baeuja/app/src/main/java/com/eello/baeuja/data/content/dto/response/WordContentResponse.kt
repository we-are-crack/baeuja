package com.eello.baeuja.data.content.dto.response

data class WordContentResponse(
    val wordId: Int,
    val koreanWord: String,
    val importance: String,
    val sentences: List<SentenceUnitResponse>
) {
    data class SentenceUnitResponse(
        val unitId: Int,
        val sentenceId: Int,
        val koreanSentence: String,
        val koreanWordInSentence: String,
        val englishSentence: String,
        val englishWordInSentence: String,
        val unitThumbnailUrl: String
    )
}