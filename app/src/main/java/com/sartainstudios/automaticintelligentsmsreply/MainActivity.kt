package com.sartainstudios.automaticintelligentsmsreply

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), MessageListener {

    companion object {
        private const val TAG = "MainActivity"
        private const val SMS_PERMISSION_CODE = 0
        private val requiredPermissions = arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS)
    }

    private var isSMSSendingDisabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Request SMS Permissions from user
        val permissionHandler = PermissionHandler(this)
        permissionHandler.requestSmsPermission(requiredPermissions, SMS_PERMISSION_CODE)

        //Register SMS Listener
        MessageReceiver.bindListener(this)

        // Continuously checks if user desires to send SMS
        switchToggleSendSMSMessages.setOnCheckedChangeListener { _, isChecked ->
            run {
                isSMSSendingDisabled = !isChecked
                Log.i(TAG, "userDisabledSMSSending: $isSMSSendingDisabled")
            }
        }
    }

    // Message received handler
    override fun messageReceived(receivedMessage: String) {
        textViewDisplayLastMessageReceived.text = receivedMessage
        Log.i(TAG, "message: $receivedMessage")

        Log.i(TAG, "userDisabledSMSSending: $isSMSSendingDisabled")

        // Send SMS if user hasn't disabled it
        if (isSMSSendingDisabled)
            return

        val outgoingMessage = editTextSetCustomMessage.text.toString()
        val phoneNumber = editTextPhoneNumber.text.toString()

        Log.i(TAG, "phoneNumber: $phoneNumber")
        Log.i(TAG, "message: $outgoingMessage")

        textViewDisplayLastMessageSent.text = outgoingMessage

        val sMSMessageSender = SMSMessageSender()
        sMSMessageSender.sendSMSMessage(phoneNumber, outgoingMessage)
        Log.i(TAG, "sMSMessageSender: $sMSMessageSender")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            SMS_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty()) {
                    for (grantResult in grantResults) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED)
                            Log.i(TAG, "Granted $grantResult")
                        else
                            Log.i(TAG, "Denied SMS_Receive Permission")
                    }
                    return
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
