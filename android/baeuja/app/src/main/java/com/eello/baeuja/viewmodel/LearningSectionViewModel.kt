package com.eello.baeuja.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class LearningItem(
    val id: Long = -1,
    val classification: ContentClassification,
    val title: String,
    val artist: String? = null,
    val director: String? = null,
    val progressRate: Int,
    val thumbnailUrl: String
)

data class LearningItemDetail(
    val id: Long,
    val classification: ContentClassification,
    val title: String,
    val artist: String? = null,
    val director: String? = null,
    val thumbnailUrl: String,
    val youtubeId: String,
    val description: String
)

@HiltViewModel
class LearningSectionViewModel @Inject constructor() : ViewModel() {
}