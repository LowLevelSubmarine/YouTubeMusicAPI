package com.lowlevelsubmarine.ytma.request

import java.net.URL

open class Request() {

    companion object {
        private val API_BASE_URL = "https://music.youtube.com/youtubei/v1/"
    }

    protected fun getEndpoint(suffix: String): URL {
        return getEndpoint(suffix,  *arrayOf())
    }

    protected fun getEndpoint(suffix: String, vararg params: Pair<String, String>): URL {
        val paramsMutable = params.toMutableList()
        paramsMutable.add("key" to "AIzaSyC9XL3ZjWddXya6X74dJoCTL-WEYFDNX30")
        val paramString = paramsMutable.joinToString("&") { it.first + "=" + it.second }
        return URL(API_BASE_URL + suffix + "?" + paramString)
    }

}