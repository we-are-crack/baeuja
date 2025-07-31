package com.eello.baeuja.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eello.baeuja.retrofit.repository.ContentRepository
import com.eello.baeuja.session.HomeContentSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class HomeLearningContent(
    val wordId: Int,
    val koreanWord: String,
    val importance: String,
    val sentences: List<LearningContentItem>
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeContentSession: HomeContentSession,
    private val contentRepository: ContentRepository
) : ViewModel() {
    private val _initLoadFailed = MutableStateFlow<Boolean>(false)
    val initLoadFailed: StateFlow<Boolean> = _initLoadFailed

    var newContents = homeContentSession.cachedNewContents
        private set

    private val _homeLearningContents =
        MutableStateFlow<List<HomeLearningContent>>(emptyList())
    val homeLearningContents: StateFlow<List<HomeLearningContent>> = _homeLearningContents

    private val usedWordId = mutableListOf<Int>()
    private var page: Int = 0

    private val _isLoadings = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoadings

    init {
        _initLoadFailed.value = homeContentSession.loadFailed

        if (_initLoadFailed.value) {
            Timber.d("홈 콘텐츠 로딩 재시도...")
            retryFetchInitHomeContents()
        }

        initStateFromSession()
    }

    private fun initStateFromSession() {
        Timber.d("HomeContentSession 로 부터 초기 상태 동기화 시도...")
        _homeLearningContents.value =
            homeContentSession.cachedLearningContents.getOrElse(page) { emptyList() }

        usedWordId.addAll(_homeLearningContents.value.map { it.wordId })

        newContents = homeContentSession.cachedNewContents
        Timber.d("HomeContentSession 상태와 동기화 성공: ${homeLearningContents.value}")
    }

    fun fetchMoreLearningContents() {
        if (_isLoadings.value) {
            return
        }

        viewModelScope.launch {
            Timber.i("fetch more learning contents: ${page + 1}페이지 홈 콘텐츠 로딩")
            _isLoadings.value = true

            Timber.d("HomeContentSession에서 ${page + 1} 페이지의 콘텐츠 로딩 시도")
            var fetchedLearningContents =
                homeContentSession.cachedLearningContents.getOrNull(++page)

            if (fetchedLearningContents == null) {
                Timber.d("HomeContentSession에 ${page + 1} 페이지의 콘텐츠 존재하지 않음 서버로부터 로딩 시도: ")
                try {
                    fetchedLearningContents =
                        contentRepository.fetchHomeLearningContents(usedWordId.toList())
                    homeContentSession.addLearningContents(fetchedLearningContents)
                } catch (e: Exception) {
                    Timber.w(e, "${page}페이지 홈 콘텐츠 로딩 실패: ${e.message}")
                    page--
                }
            }

            fetchedLearningContents?.let { contents ->
                Timber.d("${page}페이지 홈 콘텐츠 로딩 성공, 콘텐츠 수 ${contents.size}")
                Timber.d("로딩된 콘텐츠 : %s", buildString {
                    contents.forEach {
                        append("\n\t${it.wordId}: ${it.koreanWord}: sentences count: ${it.sentences.size}")
                    }
                })
                if (contents.isNotEmpty()) {
                    _homeLearningContents.value += contents
                    usedWordId.addAll(contents.map { it.wordId })
                    Timber.d("usedWordId: $usedWordId")
                }
            }

            delay(1000)
            _isLoadings.value = false
        }
    }

    fun retryFetchInitHomeContents() {
        Timber.i("초기 홈 콘텐츠 데이터 로드 재시도")
        viewModelScope.launch {
            try {
                homeContentSession.initialize()

                initStateFromSession()
                _initLoadFailed.value = false

                Timber.i("초기 홈 콘텐츠 데이터 리로드 성공")
            } catch (e: Exception) {
                Timber.i("초기 홈 콘텐츠 데이터 리로드 실패: ${e.message}")
            }
        }
    }
}