package com.sartainstudios.automaticintelligentsmsreply

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage

class MessageReceiver : BroadcastReceiver() {
    /**
     * Method triggered when broadcast received
     * @param context Message
     * @param intent Message
     */
    override fun onReceive(context: Context, intent: Intent) {
        val data = intent.extras
        val pdus = data!!.get("pdus") as Array<Any>
        for (i in pdus.indices) {
            val smsMessage = SmsMessage.createFromPdu(pdus[i] as ByteArray)
            val message = "Message: " + smsMessage.messageBody
            mListener!!.messageReceived(message)
        }
    }

    // Message receiver listener
    companion object {

        private var mListener: MessageListener? = null

        fun bindListener(listener: MessageListener) {
            mListener = listener
        }
    }
}