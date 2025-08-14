package com.eello.baeuja.retrofit.dto.response

//import com.eello.baeuja.viewmodel.HomeLearningContent
//import com.eello.baeuja.viewmodel.LearningContentItem
//
//data class HomeLearningContentsData(
//    val wordId: Int,
//    val koreanWord: String,
//    val importance: String,
//    val sentences: List<LearningSentence>
//) {
//    fun toHomeLearningContent() = HomeLearningContent(
//        wordId = wordId,
//        koreanWord = koreanWord,
//        importance = importance,
//        sentences = sentences.map { it.toLearningContentItem() }
//    )
//
//    data class LearningSentence(
//        val unitId: Int,
//        val sentenceId: Int,
//        val koreanSentence: String,
//        val koreanWordInSentence: String,
//        val englishSentence: String,
//        val englishWordInSentence: String,
//        val unitThumbnailUrl: String
//    ) {
//        fun toLearningContentItem() = LearningContentItem(
//            unitId = unitId,
//            unitThumbnailUrl = unitThumbnailUrl,
//            sentenceId = sentenceId,
//            koreanSentence = koreanSentence,
//            koreanWordInSentence = koreanWordInSentence,
//            englishSentence = englishSentence,
//            englishWordInSentence = englishWordInSentence
//        )
//    }
//}