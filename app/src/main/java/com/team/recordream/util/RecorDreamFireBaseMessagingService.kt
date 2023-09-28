package com.team.recordream.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.team.recordream.R
import com.team.recordream.presentation.MainActivity
import com.team.recordream.presentation.record.RecordActivity
import timber.log.Timber

class RecorDreamFireBaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("fcm", "onNewToken: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            val title = remoteMessage.data[TITLE] ?: ""
            val body = remoteMessage.data[BODY] ?: ""
            sendNotification(title, body)
        }
    }

    private fun getPendingIntent(requestCode: Int, flag: Int): PendingIntent {
        createNotificationChannel()
        val mainIntent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val intent = Intent(this, RecordActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addNextIntent(mainIntent)
        stackBuilder.addNextIntent(intent)
        return stackBuilder.getPendingIntent(requestCode, flag)
    }

    private fun sendNotification(title: String, body: String) {
        val pendingIntent: PendingIntent =
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        val notificationBuilder = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setColor(Color.BLACK)
            .setColorized(true)
            .setStyle(Notification.DecoratedCustomViewStyle())
            .setSmallIcon(R.drawable.ic_notification_alarm)
            .setContentIntent(pendingIntent)
            .setContentText(body)
            .setAutoCancel(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT,
            )

        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_ID = "recordDream_channel"
        const val NOTIFICATION_ID = 1
        const val CHANNEL_NAME = "recordDream_channel_name"
        const val TITLE = "title"
        const val BODY = "body"
        const val OPEN_FROM_PUSH_ALARM = "openPushAlarm"
    }
}
