package de.squiray.shutup.presentation.notification

import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import de.squiray.shutup.R
import de.squiray.shutup.presentation.service.ShutUpService
import de.squiray.shutup.presentation.ui.ShutUpActivity


class ShutUpNotification(val context: Context) {

    companion object {
        val TAG = "shutUpNotification"
        val ID = 617283123

    }

    fun notification(channelId: String, shutUp: Boolean): Notification {
        return NotificationCompat.Builder(context, channelId)
                .setContentText("Shutting up your phone on screen lock")
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setShowWhen(false)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentIntent(startShutUpActivity())
                .addAction(shutUpControlAction(shutUp))
                .build()
    }

    private fun shutUpControlAction(shutUp: Boolean): NotificationCompat.Action {
        return NotificationCompat.Action.Builder(
                R.drawable.ic_play,
                effectiveShutUpControlText(shutUp),
                shutUpControlIntent()).build()
    }

    private fun effectiveShutUpControlText(shutUp: Boolean): String {
        return if (shutUp) {
            context.getString(R.string.notification_shut_up_pause)
        } else {
            context.getString(R.string.notification_shut_up_play)
        }
    }

    private fun shutUpControlIntent(): PendingIntent {
        return PendingIntent.getService( //
                context.applicationContext, //
                0, //
                ShutUpService.shutUpControlIntent(context.applicationContext), //
                FLAG_CANCEL_CURRENT)
    }

    private fun startShutUpActivity(): PendingIntent {
        val startTheActivity = Intent(context, ShutUpActivity::class.java)
        startTheActivity.action = ACTION_MAIN
        startTheActivity.flags = FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getActivity(context, 0, startTheActivity, 0)
    }

}
