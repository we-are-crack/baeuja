package com.eello.baeuja.ui.screen.learning.mapper

import com.eello.baeuja.domain.content.model.Classification
import com.eello.baeuja.domain.content.model.ContentMeta
import com.eello.baeuja.ui.screen.learning.model.LearningContentDetailUiModel
import com.eello.baeuja.ui.screen.learning.model.LearningContentUiModel

fun ContentMeta.toLearningContentUiModel() = LearningContentUiModel(
    id = id ?: -1,
    classification = classification,
    title = title,
    artist = artist,
    director = director,
    progressRate = progressRate ?: 0,
    thumbnailUrl = thumbnailUrl
)

fun ContentMeta.toLearningContentDetailUiModel() = LearningContentDetailUiModel(
    id = id ?: -1,
    classification = classification,
    title = title,
    artist = artist,
    director = director,
    thumbnailUrl = thumbnailUrl,
    youtubeId = youtubeId ?: "",
    description = description ?: ""
)

fun Map<Classification, List<ContentMeta>>.toLearningMainContentsUiModel() =
    map { (key, value) ->
        key to value.map { it.toLearningContentUiModel() }
    }.toMap()

