package com.example.otchallenge

import android.app.Application
import com.example.otchallenge.di.AppComponent
import com.example.otchallenge.di.DaggerAppComponent

class MyApplication : Application() {

	val appComponent: AppComponent by lazy {
		DaggerAppComponent.factory().create(applicationContext)
	}
}
