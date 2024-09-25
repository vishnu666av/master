package com.example.otchallenge

import android.app.Application
import android.net.Network
import com.example.otchallenge.di.AppComponent
import com.example.otchallenge.di.DaggerAppComponent
import com.example.otchallenge.di.DatabaseModule
import com.example.otchallenge.di.NetworkModule

class MyApplication : Application() {

	lateinit var appComponent: AppComponent

	override fun onCreate() {
		super.onCreate()
		appComponent = DaggerAppComponent.builder()
			//.networkModule(NetworkModule())
			//.databaseModule(DatabaseModule(this))
			.build()
	}
}
