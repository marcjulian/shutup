package de.squiray.shutup.presentation.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import de.squiray.shutup.domain.repository.ConnectivityRepositoryImpl
import de.squiray.shutup.domain.usecase.TurnOffConnectivityUseCase
import de.squiray.shutup.domain.usecase.TurnOnConnectivityUseCase
import timber.log.Timber


class ShutUpService : Service() {

    private var screenLockReceiver: BroadcastReceiver? = null

    private var screenUnlockReceiver: ScreenUnlockReceiver? = null

    override fun onCreate() {
        super.onCreate()
        Timber.tag("ShutUpService").d("created")
        screenLockReceiver = ScreenLockReceiver()
        registerReceiver(screenLockReceiver, IntentFilter(Intent.ACTION_SCREEN_OFF))

        screenUnlockReceiver = ScreenUnlockReceiver()
        registerReceiver(screenUnlockReceiver, IntentFilter(Intent.ACTION_SCREEN_ON))
    }

    override fun onDestroy() {
        Timber.tag("ShutUpService").i("destroyed")
        unregisterReceiver(screenLockReceiver)
        unregisterReceiver(screenUnlockReceiver)
    }

    // TODO maybe remove when notification is enabled
    override fun onTaskRemoved(rootIntent: Intent?) {
        Timber.tag("ShutUpService").i("App killed by user")
        stopShutUpService()
    }

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    internal inner class ScreenLockReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action == Intent.ACTION_SCREEN_OFF) {
                Timber.tag("ShutUpService").i("ScreenLock received, turn off connectivity")

                TurnOffConnectivityUseCase(ConnectivityRepositoryImpl(this@ShutUpService.applicationContext))
                        .execute()
            }
        }
    }

    internal inner class ScreenUnlockReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action == Intent.ACTION_SCREEN_ON) {
                Timber.tag("ShutUpService").i("ScreenUnlock received, turn on connectivity")

                TurnOnConnectivityUseCase(ConnectivityRepositoryImpl(this@ShutUpService.applicationContext))
                        .execute()
            }
        }

    }

    private fun stopShutUpService() {
        val shutUpService = Intent(this@ShutUpService, ShutUpService::class.java)
        stopService(shutUpService)
    }
}
