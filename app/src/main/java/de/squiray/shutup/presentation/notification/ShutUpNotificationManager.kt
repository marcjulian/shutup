package de.squiray.shutup.presentation.notification

import android.support.v4.app.NotificationManagerCompat
import javax.inject.Inject

class ShutUpNotificationManager @Inject constructor(
        private val notificationManager: NotificationManagerCompat,
        private val shutUpNotification: ShutUpNotification
) {
    private val CHANNEL_ID = "shutUpChannelId427"

    fun notifyShutUpNotification(shutUp: Boolean) {
        notificationManager.notify(ShutUpNotification.TAG,
                ShutUpNotification.ID,
                shutUpNotification.notification(CHANNEL_ID, shutUp))
    }

    fun cancelShutUpNotification() {
        notificationManager.cancel(ShutUpNotification.TAG,
                ShutUpNotification.ID)
    }

}
