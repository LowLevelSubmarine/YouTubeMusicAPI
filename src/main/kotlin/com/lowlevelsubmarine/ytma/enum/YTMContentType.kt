package com.lowlevelsubmarine.ytma.enum

enum class YTMContentType(private val type: YTMSearchType, private val ytmName: String, private val searchParams: String) {

    SONGS(YTMSearchType.SONGS, "Song", "EgWKAQIIAWoKEAAQABAAEAAQAA=="),
    VIDEOS(YTMSearchType.VIDEOS, "Video", "EgWKAQIQAWoKEAAQABAAEAAQAA==");

    companion object {

        fun fromYTMName(ytmName: String) = values().firstOrNull { it.ytmName == ytmName }

    }

    fun getType(): YTMSearchType {
        return this.type
    }

    fun getYTMName(): String {
        return this.ytmName;
    }

    fun getSearchParams(): String {
        return this.searchParams
    }

}