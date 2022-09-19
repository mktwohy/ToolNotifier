package com.example.toolnotifier.util

import android.util.Log
import com.example.toolnotifier.businessLogic.notifying.MailingList
import com.example.toolnotifier.businessLogic.notifying.sendEmail
import com.example.toolnotifier.extensions.toast
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Error

private const val TAG = "TAG"

object Log {
    fun d(message: String, showToast: Boolean = false, sendDebugEmail: Boolean = false) {
        Log.d(TAG, message)
        toastAndEmail('d', message, showToast, sendDebugEmail)
    }

    fun i(message: String, showToast: Boolean = false, sendDebugEmail: Boolean = false) {
        Log.i(TAG, message)
        toastAndEmail('i', message, showToast, sendDebugEmail)
    }

    fun e(message: String, showToast: Boolean = false, sendDebugEmail: Boolean = false) {
        Log.e(TAG, message)
        toastAndEmail('e', message, showToast, sendDebugEmail)
    }

    fun e(error: Error, showToast: Boolean = false, sendDebugEmail: Boolean = false) {
        val message = error.message ?: "Error occurred, but no message was given"
        Log.e(TAG, message)
        toastAndEmail('e', message, showToast, sendDebugEmail)
    }

    fun e(exception: Exception, showToast: Boolean = false, sendDebugEmail: Boolean = false) {
        val message = exception.message ?: "Exception occurred, but no message was given"
        Log.e(TAG, message)
        toastAndEmail('e', message, showToast, sendDebugEmail)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun toastAndEmail(
        logType: Char,
        message: String,
        showToast: Boolean,
        sendEmail: Boolean
    ) {
        if (showToast) {
            ContextHolder.context.toast(message)
        }
        if (sendEmail) {
            GlobalScope.launch {
                sendEmail(
                    recipients = MailingList.Debug,
                    subject = "Tool Notifier Log",
                    message = "Log Type: $logType\nMessage: $message"
                )
            }
        }
    }
}
