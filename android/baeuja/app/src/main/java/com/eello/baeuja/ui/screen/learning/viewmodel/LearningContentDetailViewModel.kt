package com.eello.baeuja.ui.screen.learning.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eello.baeuja.domain.content.model.Classification
import com.eello.baeuja.domain.content.repository.ContentRepository
import com.eello.baeuja.ui.screen.learning.mapper.toLearningContentDetailUiModel
import com.eello.baeuja.ui.screen.learning.model.LearningContentDetailUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LearningContentDetailViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isFailure = MutableStateFlow(false)
    val isFailure: StateFlow<Boolean> = _isFailure

    private val _detail = MutableStateFlow<LearningContentDetailUiModel?>(null)
    val detail: StateFlow<LearningContentDetailUiModel?> = _detail

    fun loadDetailInfo(contentId: Long) {
        Timber.d("${contentId}에 대한 상세 정보 로드...")
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val detail =
                    contentRepository.getContentDetail(contentId).toLearningContentDetailUiModel()
                _detail.value = detail

                Timber.d(buildString {
                    append("상세 정보 로드 성공:\n")
                    append("\tid:\t\t${detail.id}\n")
                    append("\tclassification:\t\t${detail.classification}\n")
                    append("\ttitle:\t\t${detail.title}\n")
                    if (detail.classification == Classification.POP) {
                        append("\tartist:\t\t${detail.artist}\n")
                    } else append("\tdirector:\t\t${detail.director}\n")
                    append("\tyoutubeId:\t\t${detail.youtubeId}\n")
                    append("\tdescription:\t\t${detail.description}")
                })
            } catch (e: Exception) {
                _isFailure.value = true
                Timber.e(e, "상세 정보 로드 실패: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}