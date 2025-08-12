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
class LearningItemDetailInfoViewModel @Inject constructor(
    private val learningRepository: LearningRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isFailure = MutableStateFlow(false)
    val isFailure: StateFlow<Boolean> = _isFailure

    private val _detailInfo = MutableStateFlow<LearningItemDetail?>(null)
    val detailInfo: StateFlow<LearningItemDetail?> = _detailInfo

    fun loadDetailInfo(itemId: Long) {
        Timber.d("${itemId}에 대한 상세 정보 로드...")
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val detailInfo = learningRepository.fetchContentDetailInfo(itemId)
                _detailInfo.value = detailInfo

                Timber.d(buildString {
                    append("상세 정보 로드 성공:\n")
                    append("\tid:\t\t${detailInfo.id}\n")
                    append("\tclassification:\t\t${detailInfo.classification}\n")
                    append("\ttitle:\t\t${detailInfo.title}\n")
                    if (detailInfo.classification == ContentClassification.POP) {
                        append("\tartist:\t\t${detailInfo.artist}\n")
                    } else append("\tdirector:\t\t${detailInfo.director}\n")
                    append("\tyoutubeId:\t\t${detailInfo.youtubeId}\n")
                    append("\tdescription:\t\t${detailInfo.description}")
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