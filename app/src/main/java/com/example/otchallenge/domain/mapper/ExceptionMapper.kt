package com.example.otchallenge.domain.mapper

interface ExceptionMapper {
    fun map(exception: Throwable): String
}