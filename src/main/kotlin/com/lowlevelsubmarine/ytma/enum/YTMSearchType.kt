package com.lowlevelsubmarine.ytma.enum

enum class YTMSearchType(private val string: String, private val contentType: YTMContentType?) {

    SONGS("Songs", YTMContentType.SONGS),
    VIDEOS("Videos", YTMContentType.VIDEOS),
    TOP_RESULT("Top result", null);

    fun getString(): String {
        return this.string
    }

    fun getContentType(): YTMContentType? {
        return this.contentType
    }

}