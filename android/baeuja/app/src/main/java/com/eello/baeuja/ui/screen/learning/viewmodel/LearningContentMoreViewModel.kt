package com.eello.baeuja.ui.screen.learning.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eello.baeuja.domain.content.model.Classification
import com.eello.baeuja.domain.content.repository.ContentRepository
import com.eello.baeuja.ui.screen.learning.mapper.toLearningContentUiModel
import com.eello.baeuja.ui.screen.learning.model.LearningContentUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LearningContentMoreViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    private var page: Int = 0

    private val _items = MutableStateFlow<List<LearningContentUiModel>>(emptyList())
    val items: StateFlow<List<LearningContentUiModel>> = _items

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLastPage = MutableStateFlow(false)
    val isLastPage: StateFlow<Boolean> = _isLastPage

    fun loadMoreItems(classification: Classification, size: Int = 10) {
        if (isLastPage.value) {
            Timber.d("더 이상 로드할 학습 콘텐츠가 없음.: page = $page, isLastPage = ${isLastPage.value}")
            return
        }

        Timber.d("학습 콘텐츠 로드: classification = $classification, page = $page")
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val fetchedResult =
                    contentRepository.getContentsByClassification(classification, page, size)

                val contents = fetchedResult.contents.map { it.toLearningContentUiModel() }
                val hasNext = fetchedResult.paginationInfo.hasNext
                val pageNumber = fetchedResult.paginationInfo.pageNumber

                if (contents.isNotEmpty()) {
                    _items.value = _items.value + contents
                }

                _isLastPage.value = !hasNext
                if (hasNext) {
                    page = pageNumber + 1
                }

                Timber.d(buildString {
                    append("학습 콘텐츠 로드 성공: classification = $classification, page = $pageNumber\n")
                    append("로드된 콘텐츠 수: ${contents.size}\n")
                    contents.forEach {
                        append("\t${it}\n")
                    }

                    append("hasNext = $hasNext")
                    if (hasNext) {
                        append(", nextPage = ${pageNumber + 1}")
                    }
                })
            } catch (e: Exception) {
                Timber.e(e, "학습 콘텐츠 로드 실패: ${e.message}")
            }
        }
        _isLoading.value = false
    }
}