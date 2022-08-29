package com.example.toolnotifier

import com.example.toolnotifier.businessLogic.notifying.EmailRecipient
import com.example.toolnotifier.businessLogic.notifying.Recipient
import com.example.toolnotifier.businessLogic.notifying.SmsRecipient

object Constants {
    const val LAST_UPDATED_FILENAME = "LastUpdated.txt"
    const val HYPERKITTEN_URL = "https://www.hyperkitten.com/store/index.php"
    val MAILING_LIST = listOf(
        SmsRecipient.Michael,
        EmailRecipient.Michael
    )
}