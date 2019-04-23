package com.sartainstudios.automaticintelligentsmsreply

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log

class PermissionHandler(private val context: AppCompatActivity) {

    companion object {
        private const val TAG = "PermissionHandler"
    }

    // Request necessary requested permissions
    fun requestSmsPermission(requestedPermissions: Array<String>, requestCode: Int) {
        val nonGrantedPermissions = getListOfNeededPermissions(requestedPermissions)

        if (nonGrantedPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(context, nonGrantedPermissions, requestCode)
            nonGrantedPermissions.forEachIndexed { index, element ->
                Log.i(TAG, "Requested permission: $element")
            }
        }
    }

    // Returns permissions that have not been granted yet
    private fun getListOfNeededPermissions(requestedPermissions: Array<String>): Array<String> {
        val nonGrantedPermissionList = ArrayList<String>()

        for (requestedPermission in requestedPermissions) {
            val grant = ContextCompat.checkSelfPermission(context, requestedPermission)
            if (grant == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "$requestedPermission: granted")
                continue
            }
            nonGrantedPermissionList.add(requestedPermission)
            Log.i(TAG, "$requestedPermission: not granted")
        }
        return nonGrantedPermissionList.toArray(arrayOfNulls<String>(nonGrantedPermissionList.size))
    }
}