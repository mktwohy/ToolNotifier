package com.example.toolnotifier

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.toolnotifier.Constants.LAST_UPDATED_FILENAME

class MainActivityViewModel : ViewModel() {

    var previousDate: String? by mutableStateOf(null)
    var currentDate: String? by mutableStateOf(null)
    var isWebsiteUpdated: Boolean by mutableStateOf(false)

    val didWebsiteUpdateText by derivedStateOf {
        "Did Website Updated: $isWebsiteUpdated"
    }

    val lastUpdatedDateText by derivedStateOf {
         "Last Updated: ${ currentDate?.removePrefix("Last Updated ") }"
    }

    fun checkForUpdate() {
        with (ContextHolder.context) {
            previousDate = readTextFile(LAST_UPDATED_FILENAME)
            currentDate = getLastUpdatedDate()

            if (previousDate == null) {
                Log.i("File does not exist. Initializing file...")
                writeTextFile(LAST_UPDATED_FILENAME, currentDate ?: "")
                return
            }
        }


        isWebsiteUpdated = previousDate != currentDate
    }
}