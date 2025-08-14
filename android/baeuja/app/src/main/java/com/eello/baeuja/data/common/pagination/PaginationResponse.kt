package com.eello.baeuja.data.common.pagination

data class PaginationResponse<T>(
    val content: List<T>,
    val pageInfo: PaginationInfoResponse
) {
    data class PaginationInfoResponse(
        val pageNumber: Int,
        val pageSize: Int,
        val hasNext: Boolean,
        val hasPrevious: Boolean
    )
}