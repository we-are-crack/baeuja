package com.eello.baeuja.viewmodel

enum class ContentClassification {
    // TODO: Learning 리팩토링 끝나면 삭제해야할 파일
    DRAMA,
    MOVIE,
    POP,
}

//data class NewContentItem(
//    val classification: ContentClassification,
//    val title: String,
//    val artist: String? = null,
//    val director: String? = null,
//    val thumbnailUrl: String,
//    val unitCount: Int,
//    val wordCount: Int
//)

//@HiltViewModel
//class HomeNewContentViewModel @Inject constructor(
//    private val homeContentSession: HomeContentSession,
//    private val contentRepository: ContentRepository
//) : ViewModel() {
//
//    private val _items = MutableStateFlow<List<NewContentItem>>(emptyList())
//    val items: StateFlow<List<NewContentItem>> = _items.asStateFlow()
//
//    private var currentPage = 0
//    private var isLoading = false
//
//    init {
//        homeContentSession.cachedNewContents.getOrNull(currentPage)
//    }
//}