package com.eello.baeuja.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class LearningContentItem(
    val unitId: Int,
    val sentenceId: Int,
    val koreanSentence: String,
    val koreanWordInSentence: String,
    val englishSentence: String,
    val englishWordInSentence: String,
    val unitThumbnailUrl: String
)

@HiltViewModel
class HomeLearningContentViewModel @Inject constructor() : ViewModel() {

    private val _items = mutableStateOf<List<LearningContentItem>>(emptyList())
    val items: State<List<LearningContentItem>> = _items

    fun initItems(items: List<LearningContentItem>) {
        _items.value = items
    }
}