package id.feinn.feinnnearby.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import id.feinn.feinnnearby.R

object FeinnNotification {

    const val NOTIFICATION_CHANNEL_ID_COMMUNICATION_NEARBY_SERVICE = "feinnnearby.discovery_service"
    const val NOTIFICATION_ID_COMMUNICATION_NEARBY = 2

    private var notificationManager: NotificationManager? = null

    fun createNotificationChannels(context: Context) {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannelDiscovery = NotificationChannel(
            NOTIFICATION_CHANNEL_ID_COMMUNICATION_NEARBY_SERVICE,
            context.getString(R.string.communication_nearby_notification_channel_name),
            NotificationManager.IMPORTANCE_LOW
        )

        notificationManager!!.createNotificationChannel(notificationChannelDiscovery)
    }

    fun createNotification(
        context: Context,
        channelId: String,
        title: String,
        content: CharSequence,
        icon: Int
    ): Notification {
        return NotificationCompat
            .Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(icon)
            .build()
    }

    fun updateCommunicationNearbyService(context: Context, message: String, icon: Int) {
        notificationManager!!.notify(
            NOTIFICATION_ID_COMMUNICATION_NEARBY,
            createNotification(
                context = context,
                channelId = NOTIFICATION_CHANNEL_ID_COMMUNICATION_NEARBY_SERVICE,
                title = context.getString(R.string.app_name),
                content = message,
                icon = icon
            )
        )
    }

}