package com.example.otchallenge

import android.app.Application
import com.example.otchallenge.di.AppComponent
import com.example.otchallenge.di.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree

class MyApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
