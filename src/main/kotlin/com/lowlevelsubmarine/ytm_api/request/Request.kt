package com.lowlevelsubmarine.ytm_api.request

import com.lowlevelsubmarine.ytm_api.core.YTMA
import java.net.URL

open class Request() {

    companion object {
        private val API_BASE_URL = "https://music.youtube.com/youtubei/v1/"
    }

    fun getEndpoint(suffix: String): URL {
        return URL(API_BASE_URL + suffix + "?key=AIzaSyC9XL3ZjWddXya6X74dJoCTL-WEYFDNX30")
    }

}