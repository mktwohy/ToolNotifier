package com.example.toolnotifier.extensions

import java.text.SimpleDateFormat

val SimpleDateFormat.currentDateTime: String
    get() = this.format(System.currentTimeMillis())