package com.eello.baeuja.exception

class AppException(
    val reason: AppError,
    message: String? = reason.message,
    cause: Throwable? = null,
    val details: String? = reason.details
) : Exception(message, cause)