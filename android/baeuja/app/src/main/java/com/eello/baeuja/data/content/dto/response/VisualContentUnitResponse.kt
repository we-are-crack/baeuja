package com.eello.baeuja.data.content.dto.response

data class VisualContentUnitResponse(
    val id: Long,
    val korean: String,
    val english: String,
    val isConversation: Boolean,
    val isFamousLine: Boolean,
    val isBookmark: Boolean
)
