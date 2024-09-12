package com.example.otchallenge

import android.app.Application
import com.example.otchallenge.di.AppComponent
import com.example.otchallenge.di.AppModule
import com.example.otchallenge.di.DaggerAppComponent

class MyApplication : Application() {

	lateinit var appComponent: AppComponent

	override fun onCreate() {
		super.onCreate()
		// Initialize the Dagger component with the application instance
		appComponent = DaggerAppComponent.factory().create(AppModule(this))
	}
}
