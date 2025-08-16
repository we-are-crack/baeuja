package com.eello.baeuja.session

import com.eello.baeuja.domain.content.repository.ContentRepository
import com.eello.baeuja.ui.screen.home.HomeNewContentUiModel
import com.eello.baeuja.ui.screen.home.HomeWordContentUiModel
import com.eello.baeuja.ui.screen.home.common.mapper.toHomeNewContentUiModel
import com.eello.baeuja.ui.screen.home.common.mapper.toHomeWordContentUiModel
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeContentSession @Inject constructor() {

    private val _cachedNewContents = mutableListOf<HomeNewContentUiModel>()
    val cachedNewContents: List<HomeNewContentUiModel> = _cachedNewContents

    private val _cachedLearningContents =
        mutableListOf<List<HomeWordContentUiModel>>()
    val cachedLearningContents: List<List<HomeWordContentUiModel>> = _cachedLearningContents

    private val _usedWordId = mutableListOf<Int>()
    val usedWordId: List<Int> = _usedWordId

    var loadFailed = false
        private set

    fun initialize(
        newContents: List<HomeNewContentUiModel> = emptyList(),
        learningContents: List<HomeWordContentUiModel> = emptyList()
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

    fun addLearningContents(learningContents: List<HomeWordContentUiModel>) {
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
        var newContents: List<HomeNewContentUiModel> = emptyList()
        var learningContents: List<HomeWordContentUiModel> = emptyList()

        runCatching {
            newContents =
                contentRepository.getNewContents().map { it.toHomeNewContentUiModel() }

            learningContents =
                contentRepository.getWordContents().map { it.toHomeWordContentUiModel() }
        }.onSuccess {
            homeContentSession.initialize(newContents, learningContents)
        }.onFailure {
            Timber.e(it, "HomeContentSession 초기화 실패")
            homeContentSession.failInitContentsLoad()
        }
    }
}