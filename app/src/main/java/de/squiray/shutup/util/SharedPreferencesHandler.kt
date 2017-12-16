package de.squiray.shutup.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.util.*

class SharedPreferencesHandler constructor(context: Context) : SharedPreferences.OnSharedPreferenceChangeListener {

    private val defaultSharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val shutUpConnectivityChangedListeners = WeakHashMap<Consumer<Boolean>, Void>()

    init {
        defaultSharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    fun addShutUpConnectivityChangedListener(listener: Consumer<Boolean>) {
        shutUpConnectivityChangedListeners.put(listener, null)
        listener.accept(isShutUp())
    }

    fun isShutUp(): Boolean
            = defaultSharedPreferences.getValue(SHUT_UP_CONNECTIVITY, true)!!

    fun revertShutUp() {
        defaultSharedPreferences.setValue(SHUT_UP_CONNECTIVITY, !isShutUp())
    }

    fun shutUpWifi(): Boolean
            = defaultSharedPreferences.getValue(SHUT_UP_WIFI, true)!!

    fun wasWifiOn(): Boolean
            = defaultSharedPreferences.getValue(WIFI_WAS_ON, false)!!

    fun setWifiWasOn(wasOn: Boolean) {
        defaultSharedPreferences.setValue(WIFI_WAS_ON, wasOn)
    }

    fun revertShutUpWifi() {
        defaultSharedPreferences.setValue(SHUT_UP_WIFI, !shutUpWifi())
    }

    fun shutUpBluetooth(): Boolean
            = defaultSharedPreferences.getValue(SHUT_UP_BLUETOOTH, true)!!

    fun wasBluetoothOn(): Boolean
            = defaultSharedPreferences.getValue(BLUETOOTH_WAS_ON, false)!!

    fun setBluetoothWasOn(wasOn: Boolean) {
        defaultSharedPreferences.setValue(BLUETOOTH_WAS_ON, wasOn)
    }

    fun revertShutUpBluetooth() {
        defaultSharedPreferences.setValue(SHUT_UP_BLUETOOTH, !shutUpBluetooth())
    }

    fun debugMode(): Boolean {
        return defaultSharedPreferences.getValue(DEBUG_MODE, false)!!
    }

    fun setDebugMode(enabled: Boolean) {
        defaultSharedPreferences //
                .setValue(DEBUG_MODE, enabled)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (SHUT_UP_CONNECTIVITY == key) {
            for (listener in shutUpConnectivityChangedListeners.keys) {
                listener.accept(isShutUp())
            }
        }
    }

    companion object {

        private val SHUT_UP_CONNECTIVITY = "shutUpConnectivity"

        private val SHUT_UP_WIFI = "shutUpWifi"
        private val SHUT_UP_BLUETOOTH = "shutUpBluetooth"

        private val WIFI_WAS_ON = "wifiWasOn"
        private val BLUETOOTH_WAS_ON = "bluetoothWasOn"

        val DEBUG_MODE = "debugMode"
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    private fun SharedPreferences.clear() {
        val editor = this.edit()
        editor.clear()
        editor.apply()
    }

    private fun SharedPreferences.setValue(key: String, value: Any?) {
        set(key, value)
    }

    private operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit({ it.putString(key, value) })
            is Int -> edit({ it.putInt(key, value) })
            is Boolean -> edit({ it.putBoolean(key, value) })
            is Float -> edit({ it.putFloat(key, value) })
            is Long -> edit({ it.putLong(key, value) })
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    /**
     * finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */
    private inline fun <reified T : Any> SharedPreferences.getValue(key: String, defaultValue: T? = null): T? {
        return get(key, defaultValue)
    }

    /**
     * finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */
    private operator inline fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

}
