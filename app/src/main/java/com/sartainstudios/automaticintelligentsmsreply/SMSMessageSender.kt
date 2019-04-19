package com.sartainstudios.automaticintelligentsmsreply

import android.content.Context
import android.telephony.SmsManager
import android.widget.Toast

class SMSMessageSender {

    fun sendSMSMessage(context: Context, phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            val parts = smsManager.divideMessage(message)
            smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null)
            Toast.makeText(context, phoneNumber + " " + message, Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(context, "Failed to send", Toast.LENGTH_SHORT).show()
        }
    }
}