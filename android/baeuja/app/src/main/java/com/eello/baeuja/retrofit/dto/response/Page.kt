package com.eello.baeuja.retrofit.dto.response

data class Page<T>(
    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean
) {
    companion object {
        fun <T> of(content: List<T>, pageInfo: PageInfo) = Page(
            content = content,
            pageNumber = pageInfo.pageNumber,
            pageSize = pageInfo.pageSize,
            hasNext = pageInfo.hasNext,
            hasPrevious = pageInfo.hasPrevious
        )
    }
}