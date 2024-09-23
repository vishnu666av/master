package com.example.otchallenge.util

import android.util.Log

private const val TAG = "OTChallenge"

// Simple log debug method for calling from anywhere
fun logD(message: String) {
    Log.d(TAG, message)
}

// Simple log error method for calling from anywhere
fun logE(message: String, throwable: Throwable) {
    Log.e(TAG, message, throwable)
}