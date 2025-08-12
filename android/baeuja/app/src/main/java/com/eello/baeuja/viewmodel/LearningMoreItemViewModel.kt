package com.eello.baeuja.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eello.baeuja.retrofit.repository.LearningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LearningMoreItemViewModel @Inject constructor(
    private val learningRepository: LearningRepository
) : ViewModel() {

    private var page: Int = 0

    private val _items = MutableStateFlow<List<LearningItem>>(emptyList())
    val items: StateFlow<List<LearningItem>> = _items

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLastPage = MutableStateFlow(false)
    val isLastPage: StateFlow<Boolean> = _isLastPage

    fun loadMoreItems(classification: ContentClassification, size: Int = 10) {
        if (isLastPage.value) {
            Timber.d("더 이상 로드할 학습 콘텐츠가 없음.: page = $page, isLastPage = ${isLastPage.value}")
            return
        }

        Timber.d("학습 콘텐츠 로드: classification = $classification, page = $page")
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val fetchedResult =
                    learningRepository.fetchContentsByClassification(classification, page, size)

                val fetchedItems = fetchedResult.content
                if (fetchedItems.isNotEmpty()) {
                    _items.value = _items.value + fetchedItems
                }
                _isLastPage.value = !fetchedResult.hasNext

                Timber.d(buildString {
                    append("학습 콘텐츠 로드 성공: classification = $classification, page = $page\n")
                    append("로드된 콘텐츠 수: ${fetchedItems.size}\n")
                    fetchedItems.forEach {
                        append("\t${it}\n")
                    }

                    append("hasNext = ${fetchedResult.hasNext}")
                    if (fetchedResult.hasNext) {
                        append(", nextPage = ${fetchedResult.pageNumber + 1}")
                    }
                })

                if (fetchedResult.hasNext) {
                    page = fetchedResult.pageNumber + 1
                }
            } catch (e: Exception) {
                Timber.e(e, "학습 콘텐츠 로드 실패: ${e.message}")
            }
        }
        _isLoading.value = false
    }
}