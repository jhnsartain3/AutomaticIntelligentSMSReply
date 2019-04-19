package com.sartainstudios.automaticintelligentsmsreply

import android.content.Context
import android.telephony.SmsManager
import android.widget.Toast

class SMSMessageSender {

    fun sendSMSMessage(context: Context) {
        try {
            val phoneNumber = "5154942276"
            val message = "Hello World! Now we are going to demonstrate " +
                    "how to send a message with more than 160 characters from your Android application."
            val smsManager = SmsManager.getDefault()
            val parts = smsManager.divideMessage(message)
            smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null)
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to send", Toast.LENGTH_SHORT).show()
        }
    }
}