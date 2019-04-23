package com.sartainstudios.automaticintelligentsmsreply

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log

class MessageReceiver : BroadcastReceiver() {

    // Message receiver listener
    companion object {
        private const val TAG = "MessageReceiver"

        private var messageListener: MessageListener? = null

        fun bindListener(listener: MessageListener) {
            messageListener = listener
        }
    }

    /**
     * Method triggered when broadcast received
     * @param context Message
     * @param intent Message
     */
    override fun onReceive(context: Context, intent: Intent) {
        val data = intent.extras
        // A PDU is a “protocol data unit”, which is the industry format for an SMS message. because SMSMessage reads/writes them you shouldn't need to dissect them
        val pdus = data!!.get("pdus") as Array<*>

        Log.i(TAG, "Data: $data")
        Log.i(TAG, "Pdus: $pdus")

        for (i in pdus.indices) {
            // TODO remove deprecated code
            val smsMessage = SmsMessage.createFromPdu(pdus[i] as ByteArray)
            val message = "Message: " + smsMessage.messageBody
            messageListener!!.messageReceived(message)

            Log.i(TAG, "smsMessage: $smsMessage")
            Log.i(TAG, "message: $message")
        }
    }
}