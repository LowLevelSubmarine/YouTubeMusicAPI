package com.lowlevelsubmarine.ytma.enum

enum class YTMSection(private val string: String, private val searchParams: String) {

    SONGS("Songs" , "EgWKAQIIAWoKEAAQABAAEAAQAA=="),
    VIDEOS("Videos", "EgWKAQIQAWoKEAAQABAAEAAQAA==");

    override fun toString(): String {
        return this.string
    }

    fun getSearchParams(): String {
        return this.searchParams
    }

}