package com.eello.baeuja.retrofit.dto.response

data class MoreLearningItemsData(
    val content: List<LearningItemData>,
    val pageInfo: PageInfo
)