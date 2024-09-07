package com.example.otchallenge.data

import com.example.otchallenge.model.Fiction

class LocalFictionsRepository : Repository<Fiction> {

    override fun all(): List<Fiction> {
        return listOf()
    }
}