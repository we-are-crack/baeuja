package com.eello.baeuja.session

import com.eello.baeuja.retrofit.repository.ContentRepository
import com.eello.baeuja.viewmodel.HomeLearningContent
import com.eello.baeuja.viewmodel.NewContentItem
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeContentSession @Inject constructor() {

    private val _cachedNewContents = mutableListOf<NewContentItem>()
    val cachedNewContents: List<NewContentItem> = _cachedNewContents

    private val _cachedLearningContents =
        mutableListOf<List<HomeLearningContent>>()
    val cachedLearningContents: List<List<HomeLearningContent>> = _cachedLearningContents

    private val _usedWordId = mutableListOf<Int>()
    val usedWordId: List<Int> = _usedWordId

    var loadFailed = false
        private set

    fun initialize(
        newContents: List<NewContentItem> = emptyList(),
        learningContents: List<HomeLearningContent> = emptyList()
    ) {
        clear()

        _cachedNewContents.addAll(newContents)
        _cachedLearningContents.add(learningContents)
        _usedWordId.addAll(learningContents.map { it.wordId })

        Timber.d(buildString {
            append("HomeContentSession 초기화 성공")
            append("\n\t새로운 콘텐츠(size: ${cachedNewContents.size}): $cachedNewContents")
            append("\n\t홈 콘텐츠: $cachedLearningContents")
            append("\n\t사용된 단어 ID: $usedWordId")
        })
    }

    fun addLearningContents(learningContents: List<HomeLearningContent>) {
        _cachedLearningContents.add(learningContents)
        _usedWordId.addAll(learningContents.map { it.wordId })
    }

    fun clear() {
        _cachedNewContents.clear()
        _cachedLearningContents.clear()
        _usedWordId.clear()
        Timber.d("HomeContentSession clear")
    }

    fun failInitContentsLoad() {
        loadFailed = true
    }

    fun successInitContentLoad() {
        loadFailed = false
    }
}

@Singleton
class HomeContentSessionInitializer @Inject constructor(
    private val homeContentSession: HomeContentSession,
    private val contentRepository: ContentRepository
) : Initializer {
    override suspend fun initialize() {
        Timber.d("HomeContentSession 초기화 시도...")
        var newContents: List<NewContentItem> = emptyList()
        var learningContents: List<HomeLearningContent> = emptyList()

        runCatching {
            newContents = contentRepository.fetchHomeNewContents()
            learningContents = contentRepository.fetchHomeLearningContents()
        }.onSuccess {
            homeContentSession.initialize(newContents, learningContents)
        }.onFailure {
            Timber.e(it, "HomeContentSession 초기화 실패")
            homeContentSession.failInitContentsLoad()
        }
    }
}