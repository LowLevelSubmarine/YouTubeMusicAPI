package com.lowlevelsubmarine.ytma.entity

class SongCache(song: Song) : ContentCache(song), Song {

    private val album = song.getAlbum()

    override fun getAlbum(): String {
        return album
    }

}