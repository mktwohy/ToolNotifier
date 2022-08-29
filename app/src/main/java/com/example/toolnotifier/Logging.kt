package com.example.toolnotifier

import android.util.Log
import java.lang.Error

private const val TAG = "TAG"

object Log {
    fun d(message: String) {
        Log.d(TAG, message)
    }

    fun i(message: String) {
        Log.d(TAG, message)
    }

    fun e(message: String) {
        Log.d(TAG, message)
    }

    fun e(error: Error) {
        Log.d(TAG, error.message ?: "Error occurred, but no message was given")
    }

    fun e(exception: Exception) {
        Log.d(TAG, exception.message ?: "Error occurred, but no message was given")
    }
}
