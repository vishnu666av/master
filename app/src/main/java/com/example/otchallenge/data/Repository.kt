package com.example.otchallenge.data

interface Repository<T> {
    fun all(): List<T>
}