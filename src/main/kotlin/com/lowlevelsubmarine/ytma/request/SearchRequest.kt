package com.lowlevelsubmarine.ytma.request

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.lowlevelsubmarine.ytma.core.YTMA
import com.lowlevelsubmarine.ytma.dto.PostFields
import com.lowlevelsubmarine.ytma.entity.*
import com.lowlevelsubmarine.ytma.enum.YTMSection
import com.lowlevelsubmarine.ytma.utils.GsonUtils.Companion.surf
import com.lowlevelsubmarine.ytma.utils.StringUtils.Companion.asJsonElement
import com.lowlevelsubmarine.ytma.utils.YouTubeParsingUtils.Companion.parseMillis
import com.lowlevelsubmarine.ytma.utils.YouTubeParsingUtils.Companion.parseViews
import com.lowlevelsubmarine.ytma.utils.YouTubeParsingUtils.Companion.unwrapRuns

class SearchRequest(private val ytma: YTMA, private val query: String) : Request() {

    private val songs: List<Song>
    private val videos: List<Video>
    private val songPager = MediaTypeSearchRequest(this.ytma, this.query, YTMSection.SONGS) { SongFieldMapper(it, true).asCached() as Song }
    private val videoPager = MediaTypeSearchRequest(this.ytma, this.query, YTMSection.VIDEOS) { VideoFieldMapper(it, true).asCached() as Video }

    init {
        val postFields = PostFields()
        postFields.query = this.query
        val result = this.ytma.getHttpManager().post(getEndpoint("search"), postFields.toJson()).asJsonElement()
        val sectionList = result.surf("contents", "tabbedSearchResultsRenderer", "tabs", 0, "tabRenderer", "content", "sectionListRenderer", "contents").asJsonArray
        val songSection = sectionList.getSectionRoot(YTMSection.SONGS)?.surf("musicShelfRenderer")
        val videoSection = sectionList.getSectionRoot(YTMSection.VIDEOS)?.surf("musicShelfRenderer")
        this.songs = songSection?.asJsonObject?.extractContents { SongFieldMapper(it, false).asCached() } ?: listOf()
        this.videos = videoSection?.asJsonObject?.extractContents { VideoFieldMapper(it, false).asCached() } ?: listOf()
    }

    fun getSongs(): List<Song> {
        return this.songs
    }

    fun getSongPager(): MediaTypeSearchRequest<Song> {
        return this.songPager
    }

    fun getVideos(): List<Video> {
        return this.videos
    }

    fun getVideoPager(): MediaTypeSearchRequest<Video> {
        return this.videoPager
    }

    private inline fun <T : Content> JsonObject.extractContents(mapper: (JsonObject) -> T): List<T> {
        val list = mutableListOf<T>()
        for (entry in this.surf("contents").asJsonArray) {
            list.add(mapper(entry.surf("musicResponsiveListItemRenderer").asJsonObject))
        }
        return list
    }

    private fun JsonArray.getSectionRoot(ytmSection: YTMSection): JsonElement? {
        for (entry in this) {
            val title = entry.surf("musicShelfRenderer", "title").unwrapRuns()
            if (title == ytmSection.toString()) return entry
        }
        return null
    }

    private class VideoFieldMapper(private val root: JsonObject, isDetailedSearch: Boolean) : Video {

        private val runOffset = if (isDetailedSearch) 0 else 2

        override fun getId(): String {
            return this.root.surf("playlistItemData", "videoId").asString
        }

        override fun getTitle(): String {
            return this.root.surf("flexColumns", 0, "musicResponsiveListItemFlexColumnRenderer", "text").unwrapRuns()
        }

        override fun getAuthor(): String {
            return this.root.surf("flexColumns", 1, "musicResponsiveListItemFlexColumnRenderer", "text").unwrapRuns(runOffset)
        }

        override fun getDuration(): Long {
            return this.root.surf("flexColumns", 1, "musicResponsiveListItemFlexColumnRenderer", "text").unwrapRuns(-1).parseMillis()
        }

        override fun getViews(): Long {
            return this.root.surf("flexColumns", 1, "musicResponsiveListItemFlexColumnRenderer", "text").unwrapRuns(-3).parseViews()
        }

        fun asCached(): VideoCache {
            return VideoCache(this)
        }

    }

    private class SongFieldMapper(private val root: JsonObject, isDetailedSearch: Boolean) : Song {

        private val runOffset = if (isDetailedSearch) 0 else 2

        override fun getId(): String {
            return this.root.surf("playlistItemData", "videoId").asString
        }

        override fun getTitle(): String {
            return this.root.surf("flexColumns", 0, "musicResponsiveListItemFlexColumnRenderer", "text").unwrapRuns()
        }

        override fun getAuthor(): String {
            return this.root.surf("flexColumns", 1, "musicResponsiveListItemFlexColumnRenderer", "text").unwrapRuns(runOffset)
        }

        override fun getDuration(): Long {
            return this.root.surf("flexColumns", 1, "musicResponsiveListItemFlexColumnRenderer", "text").unwrapRuns(-1).parseMillis()
        }

        override fun getAlbum(): String {
            return this.root.surf("flexColumns", 1, "musicResponsiveListItemFlexColumnRenderer", "text").unwrapRuns(-3)
        }

        fun asCached(): SongCache {
            return SongCache(this)
        }

    }

}