package com.lowlevelsubmarine.ytma.entity

open class ContentCache(content: Content) : Content {

    private val id = content.getId()
    private val title = content.getTitle()
    private val author = content.getAuthor()
    private val duration = content.getDuration()

    override fun getId(): String {
        return this.id
    }

    override fun getTitle(): String {
        return this.title
    }

    override fun getAuthor(): String {
        return this.author
    }

    override fun getDuration(): Long {
        return this.duration
    }

}