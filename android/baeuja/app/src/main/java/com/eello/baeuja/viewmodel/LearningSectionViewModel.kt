package com.eello.baeuja.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class LearningItem(
    val id: Int = -1,
    val classification: ContentClassification,
    val title: String,
    val artist: String? = null,
    val director: String? = null,
    val progressRate: Int,
    val thumbnailUrl: String
)

@HiltViewModel
class LearningSectionViewModel @Inject constructor() : ViewModel() {
}