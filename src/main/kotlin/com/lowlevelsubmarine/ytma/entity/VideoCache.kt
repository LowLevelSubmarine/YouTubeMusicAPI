package com.lowlevelsubmarine.ytma.entity

class VideoCache(video: Video) : ContentCache(video), Video {

    private val views = video.getViews()

    override fun getViews(): Long {
        return this.views
    }

}