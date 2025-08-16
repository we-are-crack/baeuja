package com.eello.baeuja.ui.screen.learning.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eello.baeuja.domain.content.model.Classification
import com.eello.baeuja.domain.content.repository.ContentRepository
import com.eello.baeuja.ui.screen.learning.common.mapper.toLearningMainContentsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LearningMainViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val _items =
        MutableStateFlow<Map<Classification, List<LearningContentUiModel>>>(emptyMap())
    val items: StateFlow<Map<Classification, List<LearningContentUiModel>>> = _items

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isFailure = MutableStateFlow(false)
    val isFailure: StateFlow<Boolean> = _isFailure

    init {
        loadLearningMainContents()
    }

    fun loadLearningMainContents(size: Int = 5) {
        Timber.d("학습 메인 콘텐츠 로딩 시작...")
        _isLoading.value = true

        viewModelScope.launch {
            try {
                _items.value =
                    contentRepository.getMainContents(size).toLearningMainContentsUiModel()
                Timber.d(
                    buildString {
                        append("학습 메인 콘텐츠 로딩 성공: \n")
                        _items.value.forEach { (key, value) ->
                            append("\t${key}: \n")
                            value.forEach {
                                append("\t\t${it}\n")
                            }
                        }
                    }
                )

            } catch (e: Exception) {
                Timber.w(e, "학습 메인 콘텐츠 로딩 실패: ${e.message}")
                _isFailure.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}