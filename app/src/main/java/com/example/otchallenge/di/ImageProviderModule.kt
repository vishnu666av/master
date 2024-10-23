package com.example.otchallenge.di

import android.content.Context
import com.example.otchallenge.modules.ImageProvider
import dagger.Module
import dagger.Provides

@Module
class ImageProviderModule(private val context: Context) {

    @Provides
    fun provideImageProvider(): ImageProvider {
        return ImageProvider(context)
    }
}
