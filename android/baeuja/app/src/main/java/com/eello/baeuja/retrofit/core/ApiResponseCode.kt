package com.eello.baeuja.retrofit.core

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

enum class ApiResponseCode {
    SUCCESS,
    BAD_REQUEST,
    USER_NOT_FOUND,
    DUPLICATE_EMAIL,
    INVALID_NICKNAME,
    MISSING_PARAMETER,

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