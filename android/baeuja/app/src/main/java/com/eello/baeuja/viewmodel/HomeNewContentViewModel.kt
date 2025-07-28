package com.eello.baeuja.viewmodel

import androidx.lifecycle.ViewModel
import com.eello.baeuja.retrofit.repository.ContentRepository
import com.eello.baeuja.session.HomeContentSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

enum class ContentClassification {
    DRAMA,
    MOVIE,
    POP,
}

data class NewContentItem(
    val classification: ContentClassification,
    val title: String,
    val artist: String? = null,
    val director: String? = null,
    val thumbnailUrl: String,
    val unitCount: Int,
    val wordCount: Int
)

@HiltViewModel
class HomeNewContentViewModel @Inject constructor(
    private val homeContentSession: HomeContentSession,
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<NewContentItem>>(emptyList())
    val items: StateFlow<List<NewContentItem>> = _items.asStateFlow()

    private var currentPage = 0
    private var isLoading = false

    init {
        homeContentSession.cachedNewContents.getOrNull(currentPage)
    }
}