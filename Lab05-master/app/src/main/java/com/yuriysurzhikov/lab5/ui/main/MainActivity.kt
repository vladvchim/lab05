package com.yuriysurzhikov.lab5.ui.main

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yuriysurzhikov.lab5.R
import com.yuriysurzhikov.lab5.repo.SmsReceiver

class MainActivity : AppCompatActivity() {

    private lateinit var smsReceiver: SmsReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        smsReceiver = SmsReceiver()
    }

    override fun onStart() {
        super.onStart()
        if (requestPermissions()) {
            registerReceiver(
                smsReceiver, IntentFilter().apply {
                    addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
                }
            )
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(smsReceiver)
    }

    private fun requestPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECEIVE_SMS),
                REQUEST_RECEIVE_SMS_CODE
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_RECEIVE_SMS_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    registerReceiver(
                        smsReceiver, IntentFilter().apply {
                            addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
                        }
                    )
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    companion object {
        private const val REQUEST_RECEIVE_SMS_CODE = 99
    }
}