package com.example.toolnotifier

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toolnotifier.Constants.LAST_UPDATED_FILENAME
import com.example.toolnotifier.businessLogic.*
import com.example.toolnotifier.businessLogic.notifying.EmailRecipient
import com.example.toolnotifier.businessLogic.notifying.SmsRecipient
import com.example.toolnotifier.businessLogic.notifying.sendSms
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    var isUpdating: Boolean by mutableStateOf(false)

    private var lastUpdatedDate: String? by mutableStateOf(null)

    val lastUpdatedDateText by derivedStateOf {
        "Tools added on: ${lastUpdatedDate ?: "unknown"}"
    }

    init {
        lastUpdatedDate = ContextHolder.context.readTextFile(LAST_UPDATED_FILENAME)
    }

    fun checkForUpdate() {
        viewModelScope.launch {
            isUpdating = true

            val fileDate = getDateFromFile()
            val websiteDate = getDateFromWebsite(testingMode = true)

            this@MainActivityViewModel.lastUpdatedDate = websiteDate ?: fileDate

            if (websiteDate == null) {
                Log.e("Unable to get date from website", showToast = true)
                isUpdating = false
                return@launch
            }
            if (fileDate == null) {
                Log.i("File does not exist. Initializing file...", showToast = true)
            }

            writeDateToFile(websiteDate)

            if (fileDate != websiteDate) {
                onWebsiteUpdated()
            }

            isUpdating = false
        }
    }

    private suspend fun onWebsiteUpdated() {
        Log.i("Website updated with new tools!", showToast = true)

        sendSms(
            recipients = listOf(SmsRecipient.Michael, EmailRecipient.Michael),
            subject = "HyperKitten Updates",
            message = "There are new tools available on HyperKitten! \nhttps://www.hyperkitten.com/store/index.php"
        )
    }

    private fun getDateFromFile(): String? =
        ContextHolder.context.readTextFile(LAST_UPDATED_FILENAME)

    private fun writeDateToFile(date: String): Boolean =
        ContextHolder.context
            .writeTextFile(LAST_UPDATED_FILENAME, date)
            .also { writeSuccessful ->
                if (!writeSuccessful) Log.e("Unable to write file")
            }
}