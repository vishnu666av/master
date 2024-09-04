package com.example.otchallenge.di

import android.app.Application
import android.content.Context
import com.example.otchallenge.common.util.ConnectivityProvider
import com.example.otchallenge.common.util.ConnectivityProviderImpl
import com.example.otchallenge.domain.mapper.DefaultExceptionMapper
import com.example.otchallenge.domain.mapper.ExceptionMapper

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideExceptionMapper(): ExceptionMapper = DefaultExceptionMapper()

    @Provides
    @Singleton
    fun provideConnectivityProvider(
        context: Context
    ): ConnectivityProvider = ConnectivityProviderImpl(context)
}
