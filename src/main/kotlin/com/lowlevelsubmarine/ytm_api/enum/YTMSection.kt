package com.lowlevelsubmarine.ytm_api.enum

enum class YTMSection(private val string: String) {

    SONGS("Songs"),
    VIDEOS("Videos");

    override fun toString(): String {
        return this.string
    }

}