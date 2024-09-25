package com.example.otchallenge.di

import android.content.Context
import com.example.otchallenge.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {
	fun inject(activity: MainActivity)

//	@Component.Builder
//	interface Builder {
//		@BindsInstance
//		fun context(context: Context): Builder
//		fun build(): AppComponent
//	}
}
