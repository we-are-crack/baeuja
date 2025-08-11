package com.eello.baeuja.retrofit.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import java.lang.reflect.Type

class CaseInsensitiveEnumDeserializer<T : Enum<T>>(private val enumClass: Class<T>) :
    JsonDeserializer<T> {
    override fun deserialize(
        json: com.google.gson.JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): T? {
        val jsonValue = json?.asString ?: return null
        return enumClass.enumConstants?.firstOrNull {
            it.name.equals(jsonValue, ignoreCase = true)
        }
    }
}