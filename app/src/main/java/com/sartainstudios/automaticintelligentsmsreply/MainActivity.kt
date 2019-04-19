package com.sartainstudios.automaticintelligentsmsreply

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), MessageListener {
    companion object {
        private const val RECEIVE_SMS_PERMISSION_CODE = 0
        private const val SEND_SMS_PERMISSION_CODE = 2
    }

    private var userDisabledSMSSending = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Request SMS Permissions from user
        requestReceiveSmsPermission()
        //    requestSendSMSPermission()

        //Register SMS Listener
        MessageReceiver.bindListener(this)

        // Continuously check if user desires to send SMS
        switchToggleSendSMSMessages.setOnCheckedChangeListener { buttonView, isChecked ->
            run {
                userDisabledSMSSending = !isChecked
            }
        }
    }

    // Message received handler
    override fun messageReceived(message: String) {
        textViewLastMessageReceivedDisplay.text = message

        // Send SMS if user hasn't disabled it
        if (!userDisabledSMSSending) {
            val message = editTextSetCustomMessage.text.toString()
            textViewLastMessageSentDisplay.text = message
            val sMSMessageSender = SMSMessageSender()
            sMSMessageSender.sendSMSMessage(
                editTextPhoneNumber.text.toString(),
                message
            )
        }
    }

    // Request receive SMS permission
    private fun requestReceiveSmsPermission() {
        val permission = Manifest.permission.RECEIVE_SMS
        val grant = ContextCompat.checkSelfPermission(this, permission)
        Log.e("Error", permission + grant)
        val permission1 = Manifest.permission.RECEIVE_SMS
        val grant1 = ContextCompat.checkSelfPermission(this, permission1)
        Log.e("Error", permission1 + grant1)

        if (grant != PackageManager.PERMISSION_GRANTED) {
            Log.e("Error", "!granted")

            val permissionList = arrayOfNulls<String>(2)
            permissionList[0] = Manifest.permission.RECEIVE_SMS
            permissionList[1] = Manifest.permission.SEND_SMS
            ActivityCompat.requestPermissions(this, permissionList, RECEIVE_SMS_PERMISSION_CODE)
            Log.e("Error", permissionList.toString())
        }

        val permission3 = Manifest.permission.RECEIVE_SMS
        val grant3 = ContextCompat.checkSelfPermission(this, permission3)
        Log.e("Error", permission3 + grant3)
        val permission4 = Manifest.permission.RECEIVE_SMS
        val grant4 = ContextCompat.checkSelfPermission(this, permission4)
        Log.e("Error", permission4 + grant4)
    }

    // Request send SMS permission
//    private fun requestSendSMSPermission() {
//        val permission = Manifest.permission.SEND_SMS
//        val grant = ContextCompat.checkSelfPermission(this, permission)
//        if (grant != PackageManager.PERMISSION_GRANTED) {
//            val permissionList = arrayOfNulls<String>(1)
//            permissionList[0] = permission
//            ActivityCompat.requestPermissions(this, permissionList, SEND_SMS_PERMISSION_CODE)
//        }
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            RECEIVE_SMS_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Results - Receive+" + grantResults[0], Toast.LENGTH_LONG).show();
                    Log.e("Error", "Results - Receive+" + grantResults[0])

                } else {
                    Toast.makeText(this, "Results - Receive-" + grantResults[0], Toast.LENGTH_LONG).show();
                    Log.e("Error", "Results - Receive-" + grantResults[0])

                }
                return
            }

            SEND_SMS_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Results - Send+" + grantResults[0] + grantResults[2], Toast.LENGTH_LONG)
                        .show()
                    Log.e("Error", "Results - Send+" + grantResults[0] + grantResults[2])

                } else {
                    Toast.makeText(this, "Results - Send-" + grantResults[0] + grantResults[2], Toast.LENGTH_LONG)
                        .show()
                    Log.e("Error", "Results - Send-" + grantResults[0] + grantResults[2])

                }
                return
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
