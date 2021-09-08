package com.lowlevelsubmarine.ytma.entity

interface Pager<T> {

    fun getResults(): List<T>
    fun fetchNext(): List<T>

}