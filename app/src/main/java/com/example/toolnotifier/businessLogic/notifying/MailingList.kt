package com.example.toolnotifier.businessLogic.notifying

object MailingList {
    val Debug = listOf(
        EmailRecipient.MichaelDev
    )
    val Release = listOf(
        EmailRecipient.MichaelDev,
        SmsRecipient.Michael,
//        EmailRecipient.Matt,
//        SmsRecipient.Matt
    )
}
