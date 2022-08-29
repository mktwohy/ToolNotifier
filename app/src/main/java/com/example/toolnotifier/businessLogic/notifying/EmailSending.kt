package com.example.toolnotifier.businessLogic.notifying

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val emailSender = GmailSmtpServer(
    user = "mktwohy.dev@gmail.com",
    password = "ygpjbnyjodcdqwfl"
)

suspend fun sendEmail(recipients: List<String>, subject: String?, message: String) {
    withContext(Dispatchers.IO) {
        emailSender.sendMail(
            subject = subject,
            body = message,
            sender = "ADifferentSender",
            recipients = recipients
        )
    }
}