package com.upnp.fakeCall

import android.content.Context

object IvrKeyPressBroadcastSettings {
    val supportedKeys: List<Char> = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '*', '#')

    fun load(context: Context): Set<Char> {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_ENABLED_KEYS, "")
            .orEmpty()
            .asSequence()
            .filter { it in supportedKeys }
            .toSet()
    }

    fun setEnabled(context: Context, key: Char, enabled: Boolean): Set<Char> {
        if (key !in supportedKeys) return load(context)
        val updated = load(context).toMutableSet().apply {
            if (enabled) add(key) else remove(key)
        }
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_ENABLED_KEYS, supportedKeys.filter { it in updated }.joinToString(""))
            .apply()
        return updated
    }

    private const val PREFS_NAME = "fake_call_prefs"
    private const val KEY_ENABLED_KEYS = "ivr_key_press_broadcast_keys"
}
