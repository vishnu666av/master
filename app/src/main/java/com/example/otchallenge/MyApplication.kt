package com.example.otchallenge

import android.app.Application
import com.example.otchallenge.di.AppComponent
import com.example.otchallenge.di.DaggerAppComponent
import com.example.otchallenge.di.DataProviderModule
import com.example.otchallenge.di.ImageProviderModule
import com.example.otchallenge.di.NetworkProviderModule

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .networkProviderModule(NetworkProviderModule())
            .imageProviderModule(ImageProviderModule(this))
            .dataProviderModule(DataProviderModule())
            .build()
    }
}
