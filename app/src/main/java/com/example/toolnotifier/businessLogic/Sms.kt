package com.example.toolnotifier.businessLogic

enum class CellProvider(smsEmailDomain: String) {
    AT_T("@txt.att.net"),
    BOOST("@sms.myboostmobile.com"),
    CRICKET("@mms.cricketwireless.net"),
    GOOGLE_FI("@msg.fi.google.com"),
    SPRINT("@messaging.sprintpcs.com"),
    T_MOBILE("@tmomail.net"),
    VERIZON("@vtext.com")
}

fun sendEmail(recipient: String, subject: String, message: String) {

}