package com.lowlevelsubmarine.ytma.utils

import com.google.gson.JsonElement
import com.google.gson.JsonParser

class StringUtils {
    companion object {

        fun String.asJsonElement(): JsonElement {
            return JsonParser.parseString(this)
        }

    }
}