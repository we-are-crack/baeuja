package com.eello.baeuja.retrofit.dto.response

data class PageInfo(
    val pageNumber: Int,
    val pageSize: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean
)