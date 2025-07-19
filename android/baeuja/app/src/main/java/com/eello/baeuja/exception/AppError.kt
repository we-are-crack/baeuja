package com.eello.baeuja.exception

sealed class AppError {
    abstract val message: String
    abstract val details: String?

    sealed class Local : AppError() {
        data class Network(
            override val message: String = "네트워크 연결에 실패했습니다.",
            override val details: String? = null
        ) : Local()

        data class Timeout(
            override val message: String = "요청 시간이 초과되었습니다.",
            override val details: String? = null
        ) : Local()

        data class Unknown(
            override val message: String = "알 수 없는 로컬 오류입니다.",
            override val details: String? = null
        ) : Local()
    }

    sealed class Api(override val message: String) : AppError() {
        data class BadRequest(
            override val message: String = DEFAULT_MESSAGE,
            override val details: String? = null
        ) : Api(message) {
            companion object {
                const val DEFAULT_MESSAGE = "잘못된 요청입니다."
            }
        }

        data class Unauthorized(
            override val message: String = DEFAULT_MESSAGE,
            override val details: String? = null
        ) : Api(message) {
            companion object {
                const val DEFAULT_MESSAGE = "인증이 필요합니다."
            }
        }

        data class Forbidden(
            override val message: String = DEFAULT_MESSAGE,
            override val details: String? = null
        ) : Api(message) {
            companion object {
                const val DEFAULT_MESSAGE = "접근이 거부되었습니다."
            }
        }

        data class NotFound(
            override val message: String = DEFAULT_MESSAGE,
            override val details: String? = null
        ) : Api(message) {
            companion object {
                const val DEFAULT_MESSAGE = "요청한 리소스를 찾을 수 없습니다."
            }
        }

        data class Conflict(
            override val message: String = DEFAULT_MESSAGE,
            override val details: String? = null
        ) : Api(message) {
            companion object {
                const val DEFAULT_MESSAGE = "충돌이 발생했습니다."
            }
        }

        data class ServerError(
            override val message: String = DEFAULT_MESSAGE,
            override val details: String? = null
        ) : Api(message) {
            companion object {
                const val DEFAULT_MESSAGE = "서버 오류가 발생했습니다."
            }
        }

        data class EmptyResponse(
            override val message: String = DEFAULT_MESSAGE,
            override val details: String? = null
        ) : Api(message) {
            companion object {
                const val DEFAULT_MESSAGE = "응답 본문이 비어 있습니다."
            }
        }

        data class Unexpected(
            val code: Int,
            override val message: String = "예상치 못한 오류가 발생했습니다. (code: $code)",
            override val details: String? = null
        ) : Api(message)

        companion object {
            fun from(code: Int, message: String? = null, details: String? = null): Api {
                return when (code) {
                    400 -> BadRequest(message ?: BadRequest.DEFAULT_MESSAGE, details)
                    401 -> Unauthorized(message ?: Unauthorized.DEFAULT_MESSAGE, details)
                    403 -> Forbidden(message ?: Forbidden.DEFAULT_MESSAGE, details)
                    404 -> NotFound(message ?: NotFound.DEFAULT_MESSAGE, details)
                    409 -> Conflict(message ?: Conflict.DEFAULT_MESSAGE, details)
                    in 500..599 -> ServerError(message ?: ServerError.DEFAULT_MESSAGE, details)
                    else -> Unexpected(
                        code, message
                            ?: "예상치 못한 오류가 발생했습니다. (code: $code)", details
                    )
                }
            }
        }
    }
}