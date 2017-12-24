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

    fun notification(channelId: String, shutUp: Boolean): Notification {
        return NotificationCompat.Builder(context, channelId)
                .setContentTitle(effectiveShutUpTitle(shutUp))
                .setContentText(effectiveShutUpText(shutUp))
                .setOngoing(true)
                .setSmallIcon(effectiveShutUpIcon(shutUp))
                .setShowWhen(false)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentIntent(startShutUpActivity())
                .addAction(shutUpControlAction(shutUp))
                .build()
    }

    private fun effectiveShutUpIcon(shutUp: Boolean): Int {
        return if (shutUp) {
            R.drawable.ic_shut_up_notification
        } else {
            R.drawable.ic_not_shut_up_foreground
        }
    }

    private fun effectiveShutUpTitle(shutUp: Boolean): String {
        return if (shutUp) {
            context.getString(R.string.notification_shut_up_title_play)
        } else {
            context.getString(R.string.notification_shut_up_title_pause)
        }
    }

    private fun effectiveShutUpText(shutUp: Boolean): String {
        return if (shutUp) {
            context.getString(R.string.notification_shut_up_text_play)
        } else {
            context.getString(R.string.notification_shut_up_text_pause)
        }
    }

    private fun shutUpControlAction(shutUp: Boolean): NotificationCompat.Action {
        return NotificationCompat.Action.Builder(
                R.drawable.ic_play,
                effectiveShutUpControlText(shutUp),
                shutUpControlIntent()).build()
    }

    private fun effectiveShutUpControlText(shutUp: Boolean): String {
        return if (shutUp) {
            context.getString(R.string.notification_shut_up_action_pause)
        } else {
            context.getString(R.string.notification_shut_up_action_play)
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

    companion object {
        val TAG = "shutUpNotification"
        val ID = 617283123
    }
}
