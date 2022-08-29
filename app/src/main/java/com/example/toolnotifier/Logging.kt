package com.example.toolnotifier

import android.util.Log
import com.example.toolnotifier.extensions.toast
import java.lang.Error

private const val TAG = "TAG"

object Log {
    fun d(message: String, showToast: Boolean = false) {
        Log.d(TAG, message)
        if (showToast) ContextHolder.context.toast(message)
    }

    fun i(message: String, showToast: Boolean = false) {
        Log.i(TAG, message)
        if (showToast) ContextHolder.context.toast(message)
    }

    fun e(message: String, showToast: Boolean = false) {
        Log.e(TAG, message)
        if (showToast) ContextHolder.context.toast(message)
    }

    fun e(error: Error, showToast: Boolean = false) {
        val message = error.message ?: "Error occurred, but no message was given"
        Log.e(TAG, message)
        if (showToast) ContextHolder.context.toast(message)
    }

    fun e(exception: Exception, showToast: Boolean = false) {
        val message = exception.message ?: "Error occurred, but no message was given"
        Log.e(TAG, message)
        if (showToast) ContextHolder.context.toast(message)
    }
}
