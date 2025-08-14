package com.eello.baeuja.domain.content.model

data class WordContent(
    val wordId: Int,
    val koreanWord: String,
    val importance: String,
    val sentences: List<SentenceUnit>
)