package com.lowlevelsubmarine.ytma.core

import com.lowlevelsubmarine.ytma.entity.Song
import com.lowlevelsubmarine.ytma.entity.Video
import com.lowlevelsubmarine.ytma.enum.YTMContentType
import com.lowlevelsubmarine.ytma.http.DefaultHttpInterface
import com.lowlevelsubmarine.ytma.http.HttpManager
import com.lowlevelsubmarine.ytma.request.MediaTypeSearchRequest
import com.lowlevelsubmarine.ytma.request.SearchRequest
import com.lowlevelsubmarine.ytma.request.mapper.SongFieldMapper
import com.lowlevelsubmarine.ytma.request.mapper.VideoFieldMapper

class YTMA {

    companion object {
        val COOKIES = mapOf(
            "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36",
            "Accept-Language" to "en-US",
            "Content-Type" to "application/json",
            "Accept" to "application/json",
            "Referer" to "https://music.youtube.com"
        )
    }

    private val httpManager = HttpManager(COOKIES, DefaultHttpInterface.INSTANCE)

    fun getHttpManager(): HttpManager {
        return this.httpManager
    }

    fun search(query: String): SearchRequest {
        return SearchRequest(this, query)
    }

    fun songPager(query: String): MediaTypeSearchRequest<Song> {
        return MediaTypeSearchRequest(this, query, YTMContentType.SONGS) { SongFieldMapper(it, true).asCached() }
    }

    fun videoPager(query: String): MediaTypeSearchRequest<Video> {
        return MediaTypeSearchRequest(this, query, YTMContentType.SONGS) { VideoFieldMapper(it, true).asCached() }
    }


}