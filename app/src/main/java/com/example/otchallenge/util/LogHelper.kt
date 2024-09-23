package com.example.otchallenge.util

import timber.log.Timber

private const val TAG = "OTChallenge"

// Simple log debug method for calling from anywhere
fun logDebug(message: String) {
    Timber.tag(TAG).d(message)
}

// Simple log error method for calling from anywhere
fun logError(message: String, throwable: Throwable) {
    Timber.tag(TAG).e(throwable, message)
}