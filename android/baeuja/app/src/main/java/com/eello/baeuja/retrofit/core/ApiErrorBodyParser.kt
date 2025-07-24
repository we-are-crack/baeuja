package com.eello.baeuja.retrofit.core

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import javax.inject.Inject

interface ApiErrorBodyParser {
    fun parse(errorBody: ResponseBody?): ApiResponse<Unit>
}

class ApiErrorBodyParserImpl @Inject constructor(
    private val gson: Gson
) : ApiErrorBodyParser {

    override fun parse(errorBody: ResponseBody?): ApiResponse<Unit> {
        return try {
            val raw = errorBody?.string()
            raw?.let {
                val type = object : TypeToken<ApiResponse<Unit>>() {}.type
                val parsed = gson.fromJson<ApiResponse<Unit>>(it, type)
                Log.d("parseErrorBody", "에러 응답 본문 파싱 성공: $parsed")
                parsed
            } ?: run {
                Log.w("parseErrorBody", "에러 본문이 없거나 null입니다. 기본 응답으로 대체합니다.")
                ApiResponse.empty()
            }
        } catch (e: Exception) {
            Log.e("parseErrorBody", "에러 본문 파싱 중 예외 발생", e)
            Log.w("parseErrorBody", "파싱 실패로 기본 ApiResponse로 대체합니다.")

            ApiResponse(
                code = ApiResponseCode.UNKNOWN,
                message = "에러 파싱 실패",
                data = null
            )
        }
    }
}