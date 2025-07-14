package com.eello.baeuja.retrofit

import com.google.gson.Gson
import okhttp3.ResponseBody

inline fun <reified T> parseError(errorBody: ResponseBody?): T? {
    return try {
        errorBody?.charStream()?.let {
            Gson().fromJson(it, T::class.java)
        }
    } catch (e: Exception) {
        null
    }
}