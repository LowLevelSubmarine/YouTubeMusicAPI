package com.lowlevelsubmarine.ytm_api.utils

import com.google.gson.JsonElement
import com.lowlevelsubmarine.ytm_api.utils.GsonUtils.Companion.surf

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