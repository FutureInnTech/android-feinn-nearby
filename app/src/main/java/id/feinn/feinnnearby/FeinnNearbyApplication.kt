package id.feinn.feinnnearby

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat
import id.feinn.feinnnearby.data.service.DiscoveryNearbyService
import id.feinn.feinnnearby.utils.FeinnNotification

class FeinnNearbyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        FeinnNotification.createNotificationChannels(this)

        val serviceIntent = Intent(this, DiscoveryNearbyService::class.java)
        serviceIntent.action = DiscoveryNearbyService.START_DISCOVERY
        ContextCompat.startForegroundService(this, serviceIntent)
    }

}