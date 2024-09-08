package com.example.otchallenge

import android.app.Application
import com.example.otchallenge.di.AppComponent
import com.example.otchallenge.di.DaggerAppComponent
import com.example.otchallenge.di.DatabaseModule

class BooksApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .databaseModule(DatabaseModule(this))
            .build()
    }
}
