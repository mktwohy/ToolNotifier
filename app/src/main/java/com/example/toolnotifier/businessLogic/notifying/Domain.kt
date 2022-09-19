package com.example.toolnotifier.businessLogic.notifying

enum class CellProvider(val smsEmailDomain: String) {
    AT_T("txt.att.net"),
    BOOST("sms.myboostmobile.com"),
    CRICKET("sms.cricketwireless.net"),
    GOOGLE_FI("msg.fi.google.com"),
    SPRINT("messaging.sprintpcs.com"),
    T_MOBILE("tmomail.net"),
    VERIZON("vtext.com")
}

enum class EmailDomain(val domain: String) {
    GMAIL("gmail.com")
}