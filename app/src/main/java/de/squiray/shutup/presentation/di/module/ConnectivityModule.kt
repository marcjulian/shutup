package de.squiray.shutup.presentation.di.module

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.net.wifi.WifiManager
import dagger.Module
import dagger.Provides

@Module
class ConnectivityModule {

    @Provides
    fun provideBluetoothAdapter(context: Context): BluetoothAdapter {
        return (context.applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }

    @Provides
    fun provideWifiManager(context: Context): WifiManager {
        return context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }
}
