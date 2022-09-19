package com.example.toolnotifier

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toolnotifier.businessLogic.*
import com.example.toolnotifier.util.Log
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    var isUpdating: Boolean by mutableStateOf(false)

    private var _websiteUpdatedDate: String by mutableStateOf("")
    private var _lastCheckedDate: String by mutableStateOf("")

    val websiteUpdatedDate by derivedStateOf { "Tools Added: $_websiteUpdatedDate" }
    val lastCheckedDate by derivedStateOf { "Last Checked: $_lastCheckedDate" }

    init {
        loadDatesFromFiles()
        LocalFiles.registerOnWriteListener { success ->
            loadDatesFromFiles()
            if (!success) Log.e("Write Failed", showToast = true, sendDebugEmail = true)
        }
    }

    fun manuallyCheckForUpdate() {
        viewModelScope.launch {
            isUpdating = true
            checkForNewTools(manualCheck = true)
            loadDatesFromFiles()
            isUpdating = false
        }
    }

    private fun loadDatesFromFiles() {
        _websiteUpdatedDate = LocalFiles.toolsUpdatedDate.read()
        _lastCheckedDate = LocalFiles.lastCheckedDate.read()
    }
}