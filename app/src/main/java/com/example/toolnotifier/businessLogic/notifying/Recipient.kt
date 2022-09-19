package com.example.toolnotifier.businessLogic.notifying

sealed class Recipient {
    abstract val emailAddress: String
}

data class EmailRecipient(
    val username: String,
    val domain: EmailDomain
) : Recipient() {
    override val emailAddress = "$username@${domain.domain}"

    companion object {
        val Michael = "mktwohy".gmail_com
        val MichaelDev = "mktwohy.dev".gmail_com
        val Matt = "mhtwohy".gmail_com

        private val String.gmail_com: EmailRecipient
            get() = EmailRecipient(this, EmailDomain.GMAIL)

    }
}

data class SmsRecipient(
    val number: Long,
    val cellProvider: CellProvider
) : Recipient() {
    override val emailAddress = "$number@${cellProvider.smsEmailDomain}"

    companion object {
        val Michael = SmsRecipient(
            number = 651_219_9246,
            cellProvider = CellProvider.T_MOBILE
        )
        val Matt = SmsRecipient(
            number = 651_497_4149,
            cellProvider = CellProvider.AT_T
        )
    }
}
