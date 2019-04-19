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

        private var mListener: MessageListener? = null

        fun bindListener(listener: MessageListener) {
            mListener = listener
        }
    }

    /**
     * Method triggered when broadcast received
     * @param context Message
     * @param intent Message
     */
    override fun onReceive(context: Context, intent: Intent) {
        val data = intent.extras
        val pdus = data!!.get("pdus") as Array<Any>

        Log.i(TAG, "Data: " + data)
        Log.i(TAG, "Data: " + pdus)

        for (i in pdus.indices) {
            val smsMessage = SmsMessage.createFromPdu(pdus[i] as ByteArray)
            val message = "Message: " + smsMessage.messageBody
            mListener!!.messageReceived(message)

            Log.i(TAG, "smsMessage: " + smsMessage)
            Log.i(TAG, "message: " + message)
        }
    }
}