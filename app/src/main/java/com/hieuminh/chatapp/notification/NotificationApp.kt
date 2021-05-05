package com.hieuminh.chatapp.notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.os.Build
import androidx.annotation.RequiresApi
import com.hieuminh.chatapp.R
import com.hieuminh.chatapp.constant.Constant.NotificationChannel.CHANNEL_RECEIVE_NOTIFICATION

class NotificationApp : Application() {

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createReceiveNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION.SDK_INT) {
            val channelReceiveNotification = NotificationChannel(
                CHANNEL_RECEIVE_NOTIFICATION,
                getString(R.string.notification),
                IMPORTANCE_HIGH
            )
            channelReceiveNotification.description = getString(R.string.receive_notification)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channelReceiveNotification)
        }
    }
}
