package com.sartainstudios.automaticintelligentsmsreply

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), MessageListener {
    companion object {
        private const val RECEIVE_SMS_PERMISSION_CODE = 0
        private const val READ_SMS_PERMISSION_CODE = 1
        private const val SEND_SMS_PERMISSION_CODE = 2
    }

    private var userDisabledSMSSending = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        requestReceiveSmsPermission()
//        requestReadSMSPermission()
        requestSendSMSPermission()

        //Register sms listener
        MessageReceiver.bindListener(this)

        switchToggleSendSMSMessages.setOnCheckedChangeListener { buttonView, isChecked ->
            run {
                userDisabledSMSSending = !isChecked
            }
        }
    }

    override fun messageReceived(message: String) {
        textViewLastMessageReceivedDisplay.text = message
        if (!userDisabledSMSSending) {
            val message = editTextSetCustomMessage.text.toString()
            textViewLastMessageSentDisplay.text = message
            val sMSMessageSender = SMSMessageSender()
            sMSMessageSender.sendSMSMessage(
                getApplicationContext(),
                editTextPhoneNumber.text.toString(),
                message
            )
        }
    }

    private fun requestReceiveSmsPermission() {
        val permission = Manifest.permission.RECEIVE_SMS
        val grant = ContextCompat.checkSelfPermission(this, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            val permission_list = arrayOfNulls<String>(1)
            permission_list[0] = permission
            ActivityCompat.requestPermissions(this, permission_list, RECEIVE_SMS_PERMISSION_CODE)
        }
    }

    // TODO determine if needed
/*    private fun requestReadSMSPermission() {
        val permission = Manifest.permission.READ_SMS
        val grant = ContextCompat.checkSelfPermission(this, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            val permission_list = arrayOfNulls<String>(1)
            permission_list[0] = permission
            ActivityCompat.requestPermissions(this, permission_list, READ_SMS_PERMISSION_CODE)
        }
    }*/

    private fun requestSendSMSPermission() {
        val permission = Manifest.permission.SEND_SMS
        val grant = ContextCompat.checkSelfPermission(this, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            val permission_list = arrayOfNulls<String>(1)
            permission_list[0] = permission
            ActivityCompat.requestPermissions(this, permission_list, SEND_SMS_PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            RECEIVE_SMS_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Results - Receive" + grantResults[0], Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Results - Receive" + grantResults[0], Toast.LENGTH_LONG).show();
                }
                return
            }

            // TODO determine if needed
            /*  READ_SMS_PERMISSION_CODE -> {
                      if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                          Toast.makeText(this, "Results - Read" + grantResults[0] + grantResults[1], Toast.LENGTH_LONG)
                              .show();
                      } else {
                          Toast.makeText(this, "Results - Read" + grantResults[0] + grantResults[1], Toast.LENGTH_LONG)
                              .show();
                      }
                      return
                  }

                  SEND_SMS_PERMISSION_CODE -> {
                      if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                          Toast.makeText(this, "Results - Send" + grantResults[0] + grantResults[2], Toast.LENGTH_LONG)
                              .show();
                      } else {
                          Toast.makeText(this, "Results - Send" + grantResults[0] + grantResults[2], Toast.LENGTH_LONG)
                              .show();
                      }
                      return
              }*/
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
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
