package id.feinn.feinnnearby.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import id.feinn.feinnnearby.R

object FeinnNotification {

    const val NOTIFICATION_CHANNEL_ID_DISCOVERY_SERVICE = "feinnnearby.discovery_service"

    const val NOTIFICATION_ID_DISCOVERY = 2

    fun createNotificationChannels(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannelDiscovery = NotificationChannel(
            NOTIFICATION_CHANNEL_ID_DISCOVERY_SERVICE,
            context.getString(R.string.discovery_nearby_notification_channel_name),
            NotificationManager.IMPORTANCE_LOW
        )

        notificationManager.createNotificationChannel(notificationChannelDiscovery)
    }

}