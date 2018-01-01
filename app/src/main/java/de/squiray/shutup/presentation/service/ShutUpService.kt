package de.squiray.shutup.presentation.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import dagger.android.DaggerService
import de.squiray.shutup.domain.usecase.TurnOffConnectivityUseCase
import de.squiray.shutup.domain.usecase.TurnOnConnectivityUseCase
import de.squiray.shutup.presentation.notification.ShutUpNotificationManager
import de.squiray.shutup.util.Consumer
import de.squiray.shutup.util.SharedPreferencesHandler
import timber.log.Timber
import javax.inject.Inject


class ShutUpService : DaggerService() {

    @Inject
    lateinit var turnOnConnectivityUseCase: TurnOnConnectivityUseCase

    @Inject
    lateinit var turnOffConnectivityUseCase: TurnOffConnectivityUseCase

    @Inject
    lateinit var sharedPreferencesHandler: SharedPreferencesHandler

    @Inject
    lateinit var shutUpNotificationManager: ShutUpNotificationManager

    private var screenLockReceiver: BroadcastReceiver? = null

    private var screenUnlockReceiver: ScreenUnlockReceiver? = null

    override fun onCreate() {
        super.onCreate()
        Timber.tag("ShutUpService").d("created")

        screenLockReceiver = ScreenLockReceiver()
        registerReceiver(screenLockReceiver, IntentFilter(Intent.ACTION_SCREEN_OFF))

        screenUnlockReceiver = ScreenUnlockReceiver()
        registerReceiver(screenUnlockReceiver, IntentFilter(Intent.ACTION_SCREEN_ON))

        sharedPreferencesHandler.addShutUpConnectivityChangedListener(shutUpConsumer)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.tag("ShutUpService").d("started")
        if (isActionShutUpControl(intent)) {
            Timber.tag("ShutUpService").d("Received shut up control action")
            sharedPreferencesHandler.revertShutUp()
        }
        return START_STICKY
    }

    private fun isActionShutUpControl(intent: Intent?): Boolean {
        return intent != null //
                && ACTION_SHUT_UP_CONTROL == intent.action
    }

    override fun onDestroy() {
        Timber.tag("ShutUpService").i("destroyed")
        unregisterReceiver(screenLockReceiver)
        unregisterReceiver(screenUnlockReceiver)
    }

    // TODO maybe remove when notification is enabled
    override fun onTaskRemoved(rootIntent: Intent?) {
        Timber.tag("ShutUpService").i("App killed by user")
        //stopShutUpService()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    internal inner class ScreenLockReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action == Intent.ACTION_SCREEN_OFF
                    && sharedPreferencesHandler.isShutUp()) {
                Timber.tag("ShutUpService").i("ScreenLock received, turn off connectivity")

                turnOffConnectivityUseCase.execute()
            }
        }
    }

    internal inner class ScreenUnlockReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action == Intent.ACTION_SCREEN_ON
                    && sharedPreferencesHandler.isShutUp()) {
                Timber.tag("ShutUpService").i("ScreenUnlock received, turn on connectivity")

                turnOnConnectivityUseCase.execute()
            }
        }

    }

    private fun stopShutUpService() {
        val shutUpService = Intent(this@ShutUpService, ShutUpService::class.java)
        stopService(shutUpService)
    }

    companion object {
        private val ACTION_SHUT_UP_CONTROL = "actionShutUpControl"

        fun shutUpControlIntent(context: Context): Intent {
            val shutUpControlIntent = Intent(context, ShutUpService::class.java)
            shutUpControlIntent.action = ACTION_SHUT_UP_CONTROL
            return shutUpControlIntent
        }

    }

    private val shutUpConsumer = object : Consumer<Boolean> {
        override fun accept(isShutUp: Boolean) {
            shutUpNotificationManager
                    .notifyShutUpNotification(true, isShutUp)
        }
    }
}
