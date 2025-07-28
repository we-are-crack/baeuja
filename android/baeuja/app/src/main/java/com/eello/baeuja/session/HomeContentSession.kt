package com.eello.baeuja.session

import android.util.Log
import com.eello.baeuja.retrofit.repository.ContentRepository
import com.eello.baeuja.viewmodel.HomeLearningContent
import com.eello.baeuja.viewmodel.NewContentItem
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
        try {
            clear()

            val newContents = contentRepository.fetchHomeNewContents()
            val learningContents = contentRepository.fetchHomeLearningContents()

            _cachedNewContents.addAll(newContents)
            _cachedLearningContents.add(learningContents)
            _usedWordId.addAll(learningContents.map { it.wordId })

            loadFailed = false

            Log.d("HomeContentSession", "홈 콘텐츠 세션 초기화 성공")
            Log.d(
                "HomeContentSession",
                "\t새로운 콘텐츠(${cachedNewContents.size}): ${cachedNewContents}"
            )
            Log.d("HomeContentSession", "\t홈 콘텐츠: ${cachedLearningContents}")
            Log.d("HomeContentSession", "\t사용된 단어 ID: ${usedWordId}")
        } catch (e: Exception) {
            loadFailed = true
            Log.e("SplashViewModel", "홈 콘텐츠 로딩 실패: ${e.message}")
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
    }

    fun failInitContentsLoad() {
        loadFailed = true
    }

    fun successInitContentLoad() {
        loadFailed = false
    }
}