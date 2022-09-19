package com.example.toolnotifier.businessLogic

import android.annotation.SuppressLint
import com.example.toolnotifier.util.Log
import com.example.toolnotifier.businessLogic.notifying.MailingList
import com.example.toolnotifier.businessLogic.notifying.sendEmail
import com.example.toolnotifier.extensions.currentDateTime
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
private val dateFormatter = SimpleDateFormat("MM-dd hh:mm aa")

suspend fun checkForNewTools(manualCheck: Boolean = false): Boolean? {
    LocalFiles.lastCheckedDate.write(dateFormatter.currentDateTime)

    val dateFromFile = LocalFiles.toolsUpdatedDate.read()
    val dateFromWebsite = HyperKittenWebsite.parseToolsUpdatedDate()

    return when {
        dateFromWebsite == null -> {
            Log.e("Unable to get date from website", showToast = manualCheck, sendDebugEmail = true)
            null
        }
        dateFromFile.isBlank() -> {
            Log.i("File does not exist. Initializing file...", showToast = manualCheck, sendDebugEmail = true)
            LocalFiles.toolsUpdatedDate.write(dateFromWebsite)
            false
        }
        dateFromFile != dateFromWebsite -> {
            LocalFiles.toolsUpdatedDate.write(dateFromWebsite)
            Log.i("Website updated with new tools!", showToast = manualCheck)

            sendEmail(
                recipients = MailingList.Release,
                subject = "HyperKitten Updates",
                message = "There are new tools available on HyperKitten! \nhttps://www.hyperkitten.com/store/index.php"
            )
            true
        }
        else -> {
            if (!manualCheck) {
                Log.i("There are no new tools on HyperKitten.", sendDebugEmail = true)
            }
            false
        }
    }
}
