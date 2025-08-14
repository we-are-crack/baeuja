package com.eello.baeuja.data.common.pagination

import com.eello.baeuja.domain.common.pagination.model.Pagination
import com.eello.baeuja.domain.common.pagination.model.PaginationInfo

fun <T, R> PaginationResponse<T>.toDomainModel(mapper: (T) -> R) = Pagination(
    contents = content.map { mapper(it) },
    paginationInfo = PaginationInfo(
        pageNumber = pageInfo.pageNumber,
        pageSize = pageInfo.pageSize,
        hasNext = pageInfo.hasNext,
        hasPrevious = pageInfo.hasPrevious
    )
)