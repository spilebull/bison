package com.cotetsu.bison

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Firebase で Push通知
 */
class MainFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        remoteMessage?.data?.also { data ->
            val title = data["title"]
            val message = data["message"]

            // Android O(8.0) 以上で通知を表示する場合はチャンネルIDを指定する必要があるので処理を分割
            val builder = if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
                NotificationCompat.Builder(this, CHANNEL_ID)
            }
            else {
                NotificationCompat.Builder(this)
            }

            val notification = builder
                .setSmallIcon(R.mipmap.ic_launcher)     // アイコンは指定必須
                .setContentTitle(title)                 // 通知に表示されるタイトル
                .setContentText(message)                // 通知内容を設定
                .build()
            // 通知を表示します
            val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(0, notification)
        }
    }

    override fun onNewToken(instanceToken: String?) {
        // テストで使用するため、ログにトークンを出力
        Log.i("TestFCM", "token: $instanceToken")

        // 同時に通知の設定
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // Android O(8.0) 以上で通知を使用する場合は通知チャンネルを作成する必要があります
        if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
            var channel = nm.getNotificationChannel(CHANNEL_ID)
            if (channel == null) {
                channel = NotificationChannel(
                    CHANNEL_ID,
                    "プッシュ通知用のチャンネルです",
                    NotificationManager.IMPORTANCE_HIGH)
                nm.createNotificationChannel(channel)
            }
        }
    }

    companion object {
        private const val CHANNEL_ID = "com.cotetsu.bison.MAIN_CHANNEL"
    }
}