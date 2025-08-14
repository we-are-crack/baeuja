package com.eello.baeuja.domain.common.pagination.model

data class PaginationInfo(
    val pageNumber: Int,
    val hasNext: Boolean,
    val pageSize: Int,
    val hasPrevious: Boolean
)
