package com.eello.baeuja.retrofit.dto.response

import com.eello.baeuja.viewmodel.ContentClassification
import com.eello.baeuja.viewmodel.LearningItemDetail

data class LearningItemDetailInfoData(
    val id: Long,
    val classification: ContentClassification,
    val title: String,
    val artist: String? = null,
    val director: String? = null,
    val thumbnailUrl: String,
    val youtubeId: String,
    val description: String
) {
    fun toLearningItemDetail() = LearningItemDetail(
        id = id,
        classification = classification,
        title = title,
        artist = artist,
        director = director,
        thumbnailUrl = thumbnailUrl,
        youtubeId = youtubeId,
        description = description
    )
}