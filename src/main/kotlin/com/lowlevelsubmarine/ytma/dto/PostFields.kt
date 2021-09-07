package com.lowlevelsubmarine.ytma.dto

import com.lowlevelsubmarine.ytma.utils.GsonUtils

class PostFields {

    var context = Context()
    var query: String? = null
    var params: String? = null

    class Context {
        var client = Client()
        var videoId: String? = null
        var isAudioOnly: Boolean? = null
        var tunerSettingValue: String? = null
        var enablePersistentPlaylistPanel: Boolean? = null
    }

    class Client {
        var clientName = "WEB_REMIX"
        var clientVersion = "0.1"
        var gl = "US"
        var hl = "en"
    }

    fun toJson(): String {
        return GsonUtils.INSTANCE.toJson(this)
    }

}