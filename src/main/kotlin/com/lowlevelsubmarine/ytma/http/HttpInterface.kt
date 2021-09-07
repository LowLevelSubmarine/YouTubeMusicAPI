package com.lowlevelsubmarine.ytma.http

import java.net.URL

interface HttpInterface {

    fun get(url: URL, cookies: Map<String, String>): String
    fun post(url: URL, cookies: Map<String, String>, content: String): String

}