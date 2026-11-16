package com.upnp.fakeCall

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/** Restores persisted Alarm Mode alarms after the system clears them at boot or app update. */
class AlarmModeRescheduleReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_MY_PACKAGE_REPLACED -> AlarmModeScheduler.rescheduleEnabled(context)
        }
    }
}
