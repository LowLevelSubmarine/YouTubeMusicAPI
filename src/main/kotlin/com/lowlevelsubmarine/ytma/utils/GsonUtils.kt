package com.lowlevelsubmarine.ytma.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonNull

class GsonUtils {
    companion object {

        val INSTANCE = Gson()

        fun JsonElement.surf(vararg keys: Any): JsonElement {
            if (keys.isEmpty()) {
                return this
            } else {
                val key = keys[0]
                if (key is String) {
                    val obj = this.asJsonObject
                    return if (obj.has(key)) this.asJsonObject.get(key).surf(*keys.copyOfRange(1, keys.size)) else JsonNull.INSTANCE
                } else if (key is Int) {
                    val arr = this.asJsonArray
                    return if (arr.size() > key) this.asJsonArray.get(key).surf(*keys.copyOfRange(1, keys.size)) else JsonNull.INSTANCE
                } else {
                    return JsonNull.INSTANCE
                }
            }
        }

    }
}