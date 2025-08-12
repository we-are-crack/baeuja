package com.eello.baeuja.retrofit.dto.response

import com.eello.baeuja.viewmodel.ContentClassification
import com.eello.baeuja.viewmodel.LearningItem

data class LearningItemData(
    val id: Long,
    val classification: ContentClassification,
    val title: String,
    val thumbnailUrl: String,
    val progressRate: Int,
    val artist: String? = null,
    val director: String? = null
) {
    fun toLearningItem() = LearningItem(
        id = id,
        classification = classification,
        title = title,
        thumbnailUrl = thumbnailUrl,
        progressRate = progressRate,
        artist = artist,
        director = director
    )
}