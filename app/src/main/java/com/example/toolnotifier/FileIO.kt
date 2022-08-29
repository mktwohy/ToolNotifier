package com.example.toolnotifier

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.io.IOException

fun Context.readTextFile(filename: String): String? {
    return try {
        filesDir.listFiles()
            ?.firstOrNull {
                it.canRead() && it.isFile && (it.name == filename || it.name == "$filename.txt")
            }
            ?.readBytes()
            ?.toString()
    } catch (e: IOException) {
        Log.e(e)
        null
    }
}

fun Context.writeTextFile(filename: String, content: String): Boolean =
    try {
        openFileOutput("$filename.txt", MODE_PRIVATE).use { ioStream ->
            ioStream.write(content.toByteArray())
        }
        true
    } catch (e: IOException) {
        Log.e(e)
        false
    }