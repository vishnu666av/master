package com.example.otchallenge.di

import com.example.otchallenge.serialization.LocalDateTimeDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import java.time.LocalDateTime

@Module
class SerializationComponent {

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
            .create()
    }
}