package com.eello.baeuja.session

import com.eello.baeuja.retrofit.repository.ContentRepository
import com.eello.baeuja.viewmodel.HomeLearningContent
import com.eello.baeuja.viewmodel.NewContentItem
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeContentSession @Inject constructor(
    private val contentRepository: ContentRepository
) {

    private val _cachedNewContents = mutableListOf<NewContentItem>()
    val cachedNewContents: List<NewContentItem> = _cachedNewContents

    private val _cachedLearningContents =
        mutableListOf<List<HomeLearningContent>>()
    val cachedLearningContents: List<List<HomeLearningContent>> = _cachedLearningContents

    private val _usedWordId = mutableListOf<Int>()
    val usedWordId: List<Int> = _usedWordId

    var loadFailed = false
        private set

    suspend fun initialize() {
        Timber.d("HomeContetnSession 초기화 시작...")
        try {
            clear()

            val newContents = contentRepository.fetchHomeNewContents()
            val learningContents = contentRepository.fetchHomeLearningContents()

            _cachedNewContents.addAll(newContents)
            _cachedLearningContents.add(learningContents)
            _usedWordId.addAll(learningContents.map { it.wordId })

            loadFailed = false

            Timber.d(buildString {
                append("홈 콘텐츠 세션 초기화 성공")
                append("\n\t새로운 콘텐츠(size: ${cachedNewContents.size}): $cachedNewContents")
                append("\n\t홈 콘텐츠: $cachedLearningContents")
                append("\n\t사용된 단어 ID: $usedWordId")
            })
        } catch (e: Exception) {
            loadFailed = true
            Timber.e("홈 콘텐츠 로딩 실패: ${e.message}")
        }
    }

    fun addLearningContents(learningContents: List<HomeLearningContent>) {
        _cachedLearningContents.add(learningContents)
        _usedWordId.addAll(learningContents.map { it.wordId })
    }

    fun clear() {
        _cachedNewContents.clear()
        _cachedLearningContents.clear()
        _usedWordId.clear()
        Timber.d("HomeContentSession 초기화")
    }

    fun failInitContentsLoad() {
        loadFailed = true
    }

    fun successInitContentLoad() {
        loadFailed = false
    }
}