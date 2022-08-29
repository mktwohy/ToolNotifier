package com.example.toolnotifier

import org.jsoup.Jsoup

private const val HYPERKITTEN_URL = "https://www.hyperkitten.com/store/index.php"

fun getLastUpdatedDate(): String =
    Jsoup.connect(HYPERKITTEN_URL)
        .get()
        .getElementsContainingOwnText("Last Updated")
        .text()

fun main() {
    getLastUpdatedDate()
}