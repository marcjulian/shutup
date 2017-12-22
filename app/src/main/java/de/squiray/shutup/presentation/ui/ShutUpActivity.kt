package de.squiray.shutup.presentation.ui

import android.content.Intent
import de.squiray.shutup.R
import de.squiray.shutup.util.Consumer
import de.squiray.shutup.util.SharedPreferencesHandler
import kotlinx.android.synthetic.main.activity_shut_up.*
import kotlinx.android.synthetic.main.floating_action_button.*

@Activity(R.layout.activity_shut_up, R.menu.menu_shut_up)
class ShutUpActivity : BaseActivity() {

    private var sharedPreferencesHandler: SharedPreferencesHandler? = null

    override fun onMenuItemSelected(itemId: Int): Boolean {
        when (itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
        }
        return super.onMenuItemSelected(itemId)
    }

    override fun setupView() {
        sharedPreferencesHandler = SharedPreferencesHandler(this)

        sharedPreferencesHandler!!
                .addShutUpConnectivityChangedListener(shutUpConsumer)

        updateWifiConnectivity()
        updateBluetoothConnectivity()

        floatingActionButton.setOnClickListener {
            sharedPreferencesHandler!!.revertShutUp()
        }

        shutUpWifi.setOnCheckedChangeListener { _, _ ->
            sharedPreferencesHandler!!.revertShutUpWifi()
            updateWifiConnectivity()
        }
        shutUpBluetooth.setOnCheckedChangeListener { _, _ ->
            sharedPreferencesHandler!!.revertShutUpBluetooth()
            updateBluetoothConnectivity()
        }
    }

    private fun updateWifiConnectivity() {
        shutUpWifi.isChecked = sharedPreferencesHandler!!.shutUpWifi()
        if (sharedPreferencesHandler!!.shutUpWifi()) {
            wifi.setImageResource(R.drawable.ic_wifi_off)
            shutUpWifiText.setText(R.string.shut_up_wifi_on_screen_lock)
        } else {
            wifi.setImageResource(R.drawable.ic_wifi_on)
            shutUpWifiText.setText(R.string.ignore_shut_up_wifi_on_screen_lock)
        }
    }

    private fun updateBluetoothConnectivity() {
        shutUpBluetooth.isChecked = sharedPreferencesHandler!!.shutUpBluetooth()
        if (sharedPreferencesHandler!!.shutUpBluetooth()) {
            bluetooth.setImageResource(R.drawable.ic_bluetooth_off)
            shutUpBluetoothText.setText(R.string.shut_up_bluetooth_on_screen_lock)
        } else {
            bluetooth.setImageResource(R.drawable.ic_bluetooth_on)
            shutUpBluetoothText.setText(R.string.ignore_shut_up_bluetooth_on_screen_lock)
        }
    }

    private val shutUpConsumer = object : Consumer<Boolean> {
        override fun accept(enable: Boolean) {
            if (enable) {
                floatingActionButton.setImageResource(R.drawable.ic_pause)
            } else {
                floatingActionButton.setImageResource(R.drawable.ic_play)
            }
        }
    }

}
