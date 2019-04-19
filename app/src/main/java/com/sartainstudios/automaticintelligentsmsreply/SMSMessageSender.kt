package com.sartainstudios.automaticintelligentsmsreply

import android.telephony.SmsManager

class SMSMessageSender {
    /**
     * Send SMS Message
     * @param phoneNumber Phone number
     * @param message Message
     */
    fun sendSMSMessage(phoneNumber: String, message: String) {
        val smsManager = SmsManager.getDefault()
        val parts = smsManager.divideMessage(message)
        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null)
    }
}