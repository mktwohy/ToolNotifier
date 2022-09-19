package com.example.toolnotifier.util

import android.content.Context

object ContextHolder {
    private lateinit var _appContext: Context

    val context: Context
        get() = _appContext

    fun init(context: Context) {
        if (!ContextHolder::_appContext.isInitialized) {
            synchronized(this) {
                _appContext = context.applicationContext
            }
        }
    }
}