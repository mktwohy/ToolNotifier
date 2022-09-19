package com.example.toolnotifier.businessLogic.notifying

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val emailSender = GmailSmtpServer(
    user = EmailRecipient.MichaelDev.emailAddress,
    password = "ygpjbnyjodcdqwfl"
)

suspend fun sendEmail(recipients: List<Recipient>, subject: String?, message: String) {
    withContext(Dispatchers.IO) {
        emailSender.sendMail(
            subject = subject,
            body = message,
            sender = "mktwohy.dev@gmail.com",
            recipients = recipients.map { it.emailAddress }
        )
    }
}
