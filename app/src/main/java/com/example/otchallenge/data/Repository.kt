package com.example.otchallenge.data

import okio.IOException

interface Repository<T> {

    @Throws(IOException::class)
    fun all(): List<T>
}