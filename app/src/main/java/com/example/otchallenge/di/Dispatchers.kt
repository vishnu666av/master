package com.example.otchallenge.di

import javax.inject.Qualifier

@Qualifier
annotation class Dispatcher(
    val dispatcher: AppDispatchers,
)

enum class AppDispatchers {
    Default,
    IO,
}
