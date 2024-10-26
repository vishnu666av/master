package com.example.otchallenge

import android.app.Application
import com.example.otchallenge.di.AppComponent
import com.example.otchallenge.di.AppModule
import com.example.otchallenge.di.DaggerAppComponent

class MyApplication : Application() {

	lateinit var appComponent: AppComponent
	private val baseURL = "https://api.nytimes.com/"

	override fun onCreate() {
		super.onCreate()
		appComponent = DaggerAppComponent.builder()
			.appModule(AppModule(baseURL,this)).build()
	}
}
