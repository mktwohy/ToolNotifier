package com.example.toolnotifier.businessLogic

import com.example.toolnotifier.util.ContextHolder
import com.example.toolnotifier.extensions.readTextFile
import com.example.toolnotifier.extensions.writeTextFile

object LocalFiles {
    var toolsUpdatedDate = LocalFile("WebsiteUpdatedDate")
    var lastCheckedDate = LocalFile("LastCheckedDate")

    fun registerOnWriteListener(onWrite: (Boolean) -> Unit) {
        toolsUpdatedDate.registerOnWriteListener(onWrite)
        lastCheckedDate.registerOnWriteListener(onWrite)
    }
}

class LocalFile(val name: String) {
    fun read(): String =
        ContextHolder.context.readTextFile(name) ?: ""

    fun write(content: String): Boolean =
        ContextHolder.context.writeTextFile(name, content)
            .also { success ->
                listeners.forEach { it(success) }
            }

    private val listeners = mutableSetOf<(Boolean) -> Unit>()

    fun registerOnWriteListener(onWrite: (Boolean) -> Unit) {
        listeners.add(onWrite)
    }
}