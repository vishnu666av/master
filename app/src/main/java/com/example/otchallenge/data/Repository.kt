package com.example.otchallenge.data

import okio.IOException

interface Repository<T> {

    @Throws(IOException::class)
    suspend fun all(): List<T>
}