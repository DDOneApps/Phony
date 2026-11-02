package com.upnp.fakeCall

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.telecom.TelecomManager

fun showAndroid6Dialog(context: Context, onOk: () -> Unit = {}) {
    val component = ComponentName(context, FakeCallConnectionService::class.java)
    val command = "adb shell telecom set-phone-account-enabled " +
            "${component.flattenToString()} ${TelecomHelper.ACCOUNT_ID} 0"

    AlertDialog.Builder(context)
        .setTitle(R.string.android6_dialog_title)
        .setMessage(context.getString(R.string.android6_dialog_message, command))
        .setPositiveButton(R.string.android6_dialog_ok) { _, _ ->
            onOk()
            val intent = Intent(TelecomManager.ACTION_CHANGE_PHONE_ACCOUNTS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            runCatching { context.startActivity(intent) }
        }
        .setCancelable(false)
        .show()
}
