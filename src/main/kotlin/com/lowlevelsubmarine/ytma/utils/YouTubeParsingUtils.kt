package com.lowlevelsubmarine.ytma.utils

import com.google.gson.JsonElement
import com.lowlevelsubmarine.ytma.utils.GsonUtils.Companion.surf

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

    }
}