package com.example.toolnotifier.businessLogic

import com.example.toolnotifier.Constants.HYPERKITTEN_URL
import com.example.toolnotifier.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.lang.Exception


suspend fun getDateFromWebsite(testingMode: Boolean = false): String? =
    withContext(Dispatchers.IO) {
        if (testingMode) {
            delay(3000)
            "2022-08-28"
        } else {
            try {
                Jsoup.connect(HYPERKITTEN_URL)
                    .get()
                    .getElementsContainingOwnText("Last Updated")
                    .text()
                    .removePrefix("Last Updated ")
            } catch (e: Exception) {
                Log.e(e)
                null
            }
        }
    }
