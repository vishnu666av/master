package com.example.otchallenge

import android.app.Application
import com.example.otchallenge.di.AppComponent
import com.example.otchallenge.di.DaggerAppComponent
import com.example.otchallenge.di.PresentationModule

class MyApplication : Application() {

	override fun onCreate() {
		super.onCreate()
	}
}
