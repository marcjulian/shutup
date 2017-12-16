package de.squiray.shutup.presentation.notification

import android.content.Context
import android.support.v4.app.NotificationManagerCompat

class ShutUpNotificationManager(context: Context) {

    private val CHANNEL_ID = "shutUpChannelId427"

    private val notificationManager: NotificationManagerCompat
            = NotificationManagerCompat.from(context)

    private val shutUpNotification: ShutUpNotification
            = ShutUpNotification(context)

    fun notifyShutUpNotification(enable: Boolean, shutUp: Boolean) {
        if (enable) {
            notificationManager.notify(ShutUpNotification.TAG,
                    ShutUpNotification.ID,
                    shutUpNotification.notification(CHANNEL_ID, shutUp))
        } else {
            notificationManager.cancel(ShutUpNotification.TAG,
                    ShutUpNotification.ID)
        }
    }

}
