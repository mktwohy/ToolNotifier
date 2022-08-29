package com.example.toolnotifier.extensions

import android.content.Context
import android.widget.Toast
import com.example.toolnotifier.ContextHolder

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(ContextHolder.context, text, duration).show()
}