package com.eello.baeuja.ui.screen.learning.unit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eello.baeuja.domain.content.model.ContentMeta
import com.eello.baeuja.domain.content.repository.ContentRepository
import com.eello.baeuja.ui.screen.learning.common.mapper.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UnitOverviewViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UnitOverviewUiState())
    val uiState: StateFlow<UnitOverviewUiState> = _uiState

    fun loadContentUnits(contentId: Long) {
        Timber.d("콘텐츠 유닛 로드 시도...")

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val meta = loadContentMeta(contentId)
                val units = contentRepository.getContentUnits(contentId, meta.classification)

                Timber.d(buildString {
                    append("콘텐츠 유닛 로드 성공: \n")
                    append("\t로드된 유닛: ${units.size}\n")
                    units.forEachIndexed { index, it ->
                        append("\t${index + 1}: ${it}\n")
                    }
                })

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoadCompleted = true,
                    title = meta.title,
                    units = units.map { it.toUiModel() }
                )

            } catch (e: Exception) {
                Timber.e(e, "콘텐츠 유닛 로드 실패: ${e.message}")
                _uiState.value = _uiState.value.copy(
                    isFailure = true,
                    isLoading = false
                )
            }
        }
    }

    suspend fun loadContentMeta(contentId: Long): ContentMeta {
        val meta = contentRepository.getContentDetail(contentId)
        Timber.d("콘텐츠 타이틀 로드 성공: $meta")
        return meta
    }

    fun toggleBookmark(unitIndex: Int) {
        Timber.d("clicked unit: ${_uiState.value.units[unitIndex]}")
        if (_uiState.value.units[unitIndex] !is ContentUnitUiModel.VisualUnitUiModel) {
            Timber.d("북마크 버튼은 VisualUnit 에서만 가능")
            return
        }

        // TODO: 서버로 북마크 결과 전송하는 로직 추가

        _uiState.value = _uiState.value.copy(
            units = _uiState.value.units.mapIndexed { index, unit ->
                if (index == unitIndex && unit is ContentUnitUiModel.VisualUnitUiModel) {
                    val originalBookmarkValue = unit.isBookmarked

                    Timber.d(
                        "${unit.unitId}의 북마크 토글: ${originalBookmarkValue} -> ${!originalBookmarkValue}"
                    )

                    unit.copy(isBookmarked = !unit.isBookmarked)
                } else {
                    unit
                }
            }
        )
    }
}