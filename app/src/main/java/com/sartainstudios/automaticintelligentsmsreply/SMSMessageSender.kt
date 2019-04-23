package com.sartainstudios.automaticintelligentsmsreply

import android.telephony.SmsManager
import android.util.Log

class SMSMessageSender {

    companion object {
        private const val TAG = "MainActivity"
    }

    /**
     * Send SMS Message
     * @param phoneNumber Phone number
     * @param message Message
     */
    fun sendSMSMessage(phoneNumber: String, message: String) {
        val smsManager = SmsManager.getDefault()
        val parts = smsManager.divideMessage(message)
        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null)

        Log.i(TAG, "smsManager: $smsManager")
        Log.i(TAG, "parts: $parts")
        Log.i(TAG, "phoneNumber: $phoneNumber")
        Log.i(TAG, "message: $message")
    }
}