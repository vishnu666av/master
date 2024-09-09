package com.example.otchallenge.di

import com.example.otchallenge.ui.components.ConnectivityManagerNetworkMonitor
import com.example.otchallenge.ui.components.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {
    @Binds
    fun bindsNetworkManager(networkMonitor: ConnectivityManagerNetworkMonitor): NetworkMonitor
}
