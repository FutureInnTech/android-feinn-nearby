package id.feinn.feinnnearby.dataSource.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback

class DiscoveryNearbyService : Service() {

    companion object Companion {

        const val START_DISCOVERY = "id.feinn.feinnnearby.nearby.service.start_discovery"

    }

    private val discoveryNearbyBinder: DiscoveryNearbyBinder = DiscoveryNearbyBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return discoveryNearbyBinder
    }

    @Synchronized
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent?.action != null) handleAction(intent)
        // TODO Handle action null ketika service dijalankan ulang karena out of memory atau alasan lain

        return START_STICKY

    }

    private fun handleAction(intent: Intent) {

        when (intent.action!!) {
            START_DISCOVERY -> {
                // TODO Start discovery nearby device
            }
        }

    }

    inner class DiscoveryNearbyBinder : Binder()

    inner class FeinnEndpointDiscoveryCallback: EndpointDiscoveryCallback() {
        override fun onEndpointFound(
            endpointId: String,
            info: DiscoveredEndpointInfo
        ) {
            // ketika discovery ditemukan
        }

        override fun onEndpointLost(endpointId: String) {
            // ketika discovery lost
        }

    }

}