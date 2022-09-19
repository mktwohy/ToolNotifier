package com.example.toolnotifier.extensions

import android.content.Context
import android.widget.Toast
import androidx.datastore.dataStore
import androidx.work.WorkManager
import com.example.toolnotifier.businessLogic.AppSettingsSerializer
import com.example.toolnotifier.util.ContextHolder
import com.example.toolnotifier.util.Log
import java.io.IOException

val Context.dataStore by dataStore("AppSettings.json", AppSettingsSerializer)

val Context.WorkManagerInstance: WorkManager
    get() = WorkManager.getInstance(this)

fun Context.readTextFile(filename: String): String? {
    return try {
        filesDir.listFiles()
            ?.firstOrNull {
                it.canRead() && it.isFile && (it.name == filename || it.name == "$filename.txt")
            }
            ?.readText()
    } catch (e: IOException) {
        Log.e(e)
        null
    }
}

fun Context.writeTextFile(filename: String, content: String): Boolean =
    try {
        openFileOutput("$filename.txt", Context.MODE_PRIVATE).use { ioStream ->
            ioStream.write(content.toByteArray())
        }
        true
    } catch (e: IOException) {
        Log.e(e)
        false
    }

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(ContextHolder.context, text, duration).show()
}
