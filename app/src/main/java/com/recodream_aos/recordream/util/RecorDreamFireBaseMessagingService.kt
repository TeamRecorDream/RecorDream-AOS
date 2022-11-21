package com.recodream_aos.recordream.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.recodream_aos.recordream.R
import timber.log.Timber

class RecorDreamFireBaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendRegistrationToServer(token)
        Timber.tag("fcm token").d(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.data.isNotEmpty()) {
            sendNotification(
                message.data["title"].toString(),
                message.data["body"].toString()
            )
        } else {
            message.notification?.let {
                sendNotification(it.title.toString(), it.body.toString())
            }
        }
    }

    private fun getChannelId(category: String) =
        getSummaryId(category).toString() + getString(R.string.app_name)

    private fun sendNotification(title: String?, body: String) {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // 액티비티 중복 생성 방지
//        val pendingIntent = PendingIntent.getActivity(
//            this, 0, intent,
//            PendingIntent.FLAG_ONE_SHOT
//        ) // 일회성
// TODO: channerl id 입력해야됨
        val channelId = getChannelId(""  ) // 채널 아이디
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION) // 소리
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title) // 제목
            .setContentText(body) // 내용
            .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
            .setSound(defaultSoundUri)
//            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 버전 예외처리
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelId,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
            with(NotificationManagerCompat.from(this)) {
                notify(1000, notificationBuilder.build())
            }
        } else {

            notificationManager.notify(0, notificationBuilder.build()) // 알림 생성
        }
    }

    private fun getSummaryId(category: String) = when (category) {
        NotificationCategory.CERTIFICATION.category -> NotificationCategory.CERTIFICATION.summaryId
        NotificationCategory.RECORED.category -> NotificationCategory.RECORED.summaryId
        else -> throw IllegalArgumentException("FCM category 필드 오류")
    }

    companion object {
        fun getDeviceToken() {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.d("TokenTest", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
                Timber.d("TokenTest", token!!)
            })
        }

        private const val TAG = "MyFirebaseMsgService"
    }

    private fun sendRegistrationToServer(token: String) {    //토큰 갱신을 위한
//        val body = RequestRefreshToken(token)
//서버한테 토큰이 바뀌었다는 것을 또 알려줘야 되는거 작성해야됨
    }

}