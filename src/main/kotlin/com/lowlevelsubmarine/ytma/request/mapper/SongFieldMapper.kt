package com.lowlevelsubmarine.ytma.request.mapper

import com.google.gson.JsonObject
import com.lowlevelsubmarine.ytma.entity.Song
import com.lowlevelsubmarine.ytma.entity.SongCache
import com.lowlevelsubmarine.ytma.utils.GsonUtils.Companion.surf
import com.lowlevelsubmarine.ytma.utils.YouTubeParsingUtils.Companion.parseMillis
import com.lowlevelsubmarine.ytma.utils.YouTubeParsingUtils.Companion.unwrapRuns

class SongFieldMapper(private val root: JsonObject, isDetailedSearch: Boolean) : Song {

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