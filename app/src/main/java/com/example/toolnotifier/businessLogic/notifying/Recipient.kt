package com.example.toolnotifier.businessLogic.notifying

sealed class Recipient {
    abstract val emailAddress: String
}

data class EmailRecipient(
    val username: String,
    val domain: String
) : Recipient() {
    override val emailAddress = "$username@$domain"

    companion object {
        val Michael = EmailRecipient(
            username = "mktwohy",
            domain = "gmail.com"
        )
    }
}

data class SmsRecipient(
    val number: Long,
    val cellProvider: CellProvider
) : Recipient() {
    override val emailAddress = "$number${cellProvider.smsEmailDomain}"

    companion object {
        val Michael = SmsRecipient(
            number = 651_219_9246,
            cellProvider = CellProvider.T_MOBILE
        )
    }
}

enum class CellProvider(val smsEmailDomain: String) {
    AT_T("@txt.att.net"),
    BOOST("@sms.myboostmobile.com"),
    CRICKET("@mms.cricketwireless.net"),
    GOOGLE_FI("@msg.fi.google.com"),
    SPRINT("@messaging.sprintpcs.com"),
    T_MOBILE("@tmomail.net"),
    VERIZON("@vtext.com")
}