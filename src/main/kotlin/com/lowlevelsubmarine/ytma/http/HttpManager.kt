package com.lowlevelsubmarine.ytma.http

import java.net.URL

class HttpManager(private val defaultCookies: Map<String, String>, private val httpInterface: HttpInterface) {

    fun get(url: URL): String {
        return get(url, this.defaultCookies)
    }

    fun get(url: URL, cookies: Map<String, String>): String {
        return this.httpInterface.get(url, cookies)
    }

    fun post(url: URL, content: String): String {
        return post(url, this.defaultCookies, content)
    }

    fun post(url: URL, cookies: Map<String, String>, content: String): String {
        return this.httpInterface.post(url, cookies, content);
    }

}