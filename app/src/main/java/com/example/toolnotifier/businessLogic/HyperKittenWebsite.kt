package com.example.toolnotifier.businessLogic

import com.example.toolnotifier.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.lang.Exception
import kotlin.random.Random

fun Int.padZeros(length: Int): String =
    this.toString()
        .padStart(length, '0')

object HyperKittenWebsite {
    const val URL = "https://www.hyperkitten.com/store/index.php"

    suspend fun parseToolsUpdatedDate(testingMode: Boolean = false): String? =
        withContext(Dispatchers.IO) {
            if (testingMode) {
                delay(3000)
                val month = Random.nextInt(from = 0, until = 13).padZeros(2)
                val day = Random.nextInt(from = 0, until = 33).padZeros(2)
                "$month-$day"
            } else {
                try {
                    Jsoup.connect(URL)
                        .get()
                        .getElementsContainingOwnText("Last Updated")
                        .text()
                        .removePrefix("Last Updated ")
                        .split('-')
                        .let {
                            val (year, month, day) = it
                            "$month-$day"
                        }
                } catch (e: Exception) {
                    Log.e(e)
                    null
                }
            }
        }
}

