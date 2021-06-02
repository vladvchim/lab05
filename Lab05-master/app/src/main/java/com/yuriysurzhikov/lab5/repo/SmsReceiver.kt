package com.yuriysurzhikov.lab5.repo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.widget.Toast


class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            Telephony.Sms.Intents.getMessagesFromIntent(intent).forEach {
                val msg = "From ${it.originatingAddress}: ${it.messageBody}"
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            }
        }
    }
}