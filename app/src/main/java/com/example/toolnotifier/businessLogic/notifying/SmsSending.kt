package com.example.toolnotifier.businessLogic.notifying


suspend fun sendSms(recipients: List<Recipient>, subject: String?, message: String) {
    sendEmail(
        recipients = recipients.map { it.emailAddress },
        subject = subject,
        message = message
    )
}