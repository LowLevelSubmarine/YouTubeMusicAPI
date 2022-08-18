package com.lowlevelsubmarine.ytma.request

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.lowlevelsubmarine.ytma.core.YTMA
import com.lowlevelsubmarine.ytma.dto.PostFields
import com.lowlevelsubmarine.ytma.entity.Content
import com.lowlevelsubmarine.ytma.entity.Pager
import com.lowlevelsubmarine.ytma.enum.YTMContentType
import com.lowlevelsubmarine.ytma.utils.GsonUtils.Companion.surf
import com.lowlevelsubmarine.ytma.utils.StringUtils.Companion.asJsonElement

class MediaTypeSearchRequest<T : Content>(
    private val ytma: YTMA,
    private val query: String,
    private val mediaType: YTMContentType,
    private val mapper: (JsonObject) -> T
) : Request(), Pager<T> {

    private val results: MutableList<T> = mutableListOf()
    private var continuations: String? = null

    init {
        fetchNext()
    }

    override fun getResults(): List<T> {
        return this.results.toList()
    }

    override fun fetchNext(): List<T> {
        val postFields = PostFields()
        postFields.query = this.query
        postFields.params = this.mediaType.getSearchParams()
        val musicShelf = if (this.continuations != null) {
            val result = this.ytma.getHttpManager().post(getEndpoint("search", "type" to "next", "continuation" to this.continuations!!), postFields.toJson()).asJsonElement()
            result.surf("continuationContents", "musicShelfContinuation")
        } else {
            val result = this.ytma.getHttpManager().post(getEndpoint("search"), postFields.toJson()).asJsonElement()
            result.surf("contents", "tabbedSearchResultsRenderer", "tabs", 0, "tabRenderer", "content", "sectionListRenderer", "contents", 1, "musicShelfRenderer")
        }
        return if (musicShelf != JsonNull.INSTANCE) extractResults(musicShelf) else listOf()
    }

    private fun extractResults(root: JsonElement): List<T> {
        this.continuations = root.surf("continuations", 0, "nextContinuationData", "continuation").asString
        val resultList = mutableListOf<T>()
        for (entry in root.surf("contents").asJsonArray) {
            resultList.add(this.mapper(entry.surf("musicResponsiveListItemRenderer").asJsonObject))
        }
        this.results.addAll(resultList)
        return resultList
    }

}