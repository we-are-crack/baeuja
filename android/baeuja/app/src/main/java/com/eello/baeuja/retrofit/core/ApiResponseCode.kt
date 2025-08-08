package com.eello.baeuja.retrofit.core

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

enum class ApiResponseCode {
    SUCCESS,

    BAD_REQUEST,
    DUPLICATE_EMAIL,
    DUPLICATE_NICKNAME,
    INVALID_NICKNAME,
    INVALID_QUERY_PARAMETER_TYPE,
    INVALID_SIGN_UP_REQUEST_BODY,

    USER_NOT_FOUND,
    MISSING_PARAMETER,
    INVALID_TOKEN,
    EXPIRED_TOKEN,

    UNKNOWN;

    companion object {
        fun from(code: String?): ApiResponseCode {
            return try {
                valueOf(code?.uppercase() ?: "")
            } catch (e: Exception) {
                UNKNOWN
            }
        }
    }
}

class ApiResponseCodeAdapter : JsonDeserializer<ApiResponseCode> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ApiResponseCode {
        return ApiResponseCode.from(json?.asString)
    }
}