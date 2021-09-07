package com.lowlevelsubmarine.ytma.utils

import com.google.gson.JsonElement
import com.lowlevelsubmarine.ytma.utils.GsonUtils.Companion.surf
import java.util.regex.Pattern
import kotlin.math.pow

class YouTubeParsingUtils {
    companion object {

        fun JsonElement.unwrapRuns(): String {
            return unwrapRuns(0)
        }

        fun JsonElement.unwrapRuns(index: Int): String {
            val runs = this.surf("runs").asJsonArray
            val i = if (index < 0) runs.size() + index else index
            return runs.surf(i, "text").asString
        }

        fun String.parseMillis(): Long {
            var length = 0
            for (part in this.split("[:.]".toRegex()).toTypedArray()) {
                length = length * 60 + Integer.valueOf(part)
            }
            return length * 1000L
        }

        fun String.parseViews(): Long {
            val matcher = Pattern.compile("([\\d.]{1,3})(\\S)* views").matcher(this)
            if (matcher.find()) {
                val value = matcher.group(1).toFloat()
                if (matcher.group(2) != null) {
                    return when (matcher.group(2)) {
                        "B" -> value * 1000000000
                        "M" -> value * 1000000
                        "K" -> value * 1000
                        else -> 0
                    }.toLong()
                } else {
                    return value.toLong()
                }
            }
            return -1
        }

    }
}