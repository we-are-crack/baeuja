package com.eello.baeuja.domain.common.pagination.model

data class Pagination<T>(
    val contents: List<T>,
    val paginationInfo: PaginationInfo
)
