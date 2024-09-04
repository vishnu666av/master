package com.example.otchallenge.common.util

import android.content.Context
import javax.inject.Inject

class ConnectivityProviderImpl @Inject constructor(
    private val context: Context
) : ConnectivityProvider {

    override fun isNetworkAvailable(): Boolean {
        return NetworkUtil.isNetworkAvailable(context)
    }
}