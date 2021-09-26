package com.lowlevelsubmarine.ytma.request

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.lowlevelsubmarine.ytma.core.YTMA
import com.lowlevelsubmarine.ytma.dto.PostFields
import com.lowlevelsubmarine.ytma.entity.*
import com.lowlevelsubmarine.ytma.enum.YTMContentType
import com.lowlevelsubmarine.ytma.enum.YTMSearchType
import com.lowlevelsubmarine.ytma.request.mapper.SongFieldMapper
import com.lowlevelsubmarine.ytma.request.mapper.VideoFieldMapper
import com.lowlevelsubmarine.ytma.utils.GsonUtils.Companion.surf
import com.lowlevelsubmarine.ytma.utils.StringUtils.Companion.asJsonElement
import com.lowlevelsubmarine.ytma.utils.YouTubeParsingUtils.Companion.unwrapRuns

class SearchRequest(private val ytma: YTMA, private val query: String) : Request() {

    private val songs: List<Song>
    private val videos: List<Video>
    private val topResult: Content?

    init {
        val postFields = PostFields()
        postFields.query = this.query
        val result = this.ytma.getHttpManager().post(getEndpoint("search"), postFields.toJson()).asJsonElement()
        val sectionList = result.surf("contents", "tabbedSearchResultsRenderer", "tabs", 0, "tabRenderer", "content", "sectionListRenderer", "contents").asJsonArray
        val songSection = sectionList.getSectionRoot(YTMSearchType.SONGS)?.surf("musicShelfRenderer")
        val videoSection = sectionList.getSectionRoot(YTMSearchType.VIDEOS)?.surf("musicShelfRenderer")
        val topResultSection = sectionList.getSectionRoot(YTMSearchType.TOP_RESULT)?.surf("musicShelfRenderer")
        this.songs = songSection?.asJsonObject?.extractContents { SongFieldMapper(it, false).asCached() } ?: listOf()
        this.videos = videoSection?.asJsonObject?.extractContents { VideoFieldMapper(it, false).asCached() } ?: listOf()
        this.topResult = topResultSection?.asJsonObject?.extractTopResult()
    }

    fun getSongs(): List<Song> {
        return this.songs
    }

    fun getSongPager(): MediaTypeSearchRequest<Song> {
        return this.ytma.songPager(this.query)
    }

    fun getVideos(): List<Video> {
        return this.videos
    }

    fun getVideoPager(): MediaTypeSearchRequest<Video> {
        return this.ytma.videoPager(this.query)
    }

    private inline fun <T : Content> JsonObject.extractContents(mapper: (JsonObject) -> T): List<T> {
        val list = mutableListOf<T>()
        for (entry in this.surf("contents").asJsonArray) {
            list.add(mapper(entry.surf("musicResponsiveListItemRenderer").asJsonObject))
        }
        return list
    }

    private fun JsonObject.extractTopResult(): Content? {
        val listItemRenderer = this.surf("contents", 0, "musicResponsiveListItemRenderer").asJsonObject
        val ytmName = listItemRenderer.surf("flexColumns", 1, "musicResponsiveListItemFlexColumnRenderer", "text").unwrapRuns(0)
        val contentType = YTMContentType.fromYTMName(ytmName);
        if (contentType == YTMContentType.SONGS) {
            return SongFieldMapper(listItemRenderer, false).asCached()
        } else if (contentType == YTMContentType.VIDEOS) {
            return VideoFieldMapper(listItemRenderer, false).asCached()
        } else {
            return null
        }
    }

    private fun JsonArray.getSectionRoot(ytmSearchType: YTMSearchType): JsonElement? {
        for (entry in this) {
            val title = entry.surf("musicShelfRenderer", "title").unwrapRuns()
            if (title == ytmSearchType.getString()) return entry
        }
        return null
    }

}