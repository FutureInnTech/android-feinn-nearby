package id.feinn.feinnnearby.data.service

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import com.google.android.gms.nearby.connection.Strategy
import id.feinn.feinnnearby.R
import id.feinn.feinnnearby.model.NearbyDevice
import id.feinn.feinnnearby.utils.FeinnNotification

class DiscoveryNearbyService : Service() {

    companion object Companion {

        const val START_DISCOVERY = "id.feinn.feinnnearby.nearby.service.start_discovery"
        const val STOP_DISCOVERY = "id.feinn.feinnnearby.nearby.service.stop_discovery"

        private const val DISCOVERY_SERVICE_ID = "feinnnearby.discovery_service"

    }

    private val discoveryNearbyBinder: DiscoveryNearbyBinder = DiscoveryNearbyBinder()
    private val endpointDiscoveryCallback: FeinnEndpointDiscoveryCallback =
        FeinnEndpointDiscoveryCallback()
    private val connectionClient by lazy { Nearby.getConnectionsClient(this) }
    private var listener: DiscoveryNearbyListener? = null

    private lateinit var notificationManager: NotificationManager

    override fun onBind(intent: Intent?): IBinder? {
        return discoveryNearbyBinder
    }

    override fun onCreate() {
        super.onCreate()

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        startForeground()
    }

    @Synchronized
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action != null) handleAction(intent)
        // TODO Handle action null ketika service dijalankan ulang karena out of memory atau alasan lain

        return START_STICKY

    }

    private fun startForeground() {
        val serviceNotification = createNotification(false, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ServiceCompat.startForeground(
                this,
                FeinnNotification.NOTIFICATION_ID_DISCOVERY,
                serviceNotification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE
            )
        } else {
            ServiceCompat.startForeground(this, FeinnNotification.NOTIFICATION_ID_DISCOVERY, serviceNotification, 0)
        }
    }

    private fun handleAction(intent: Intent) {

        when (intent.action!!) {
            START_DISCOVERY -> {
                startDiscovery()
            }

            STOP_DISCOVERY -> {
                stopDiscovery()
            }
        }

    }

    private fun startDiscovery() {
        val discoveryOptions = DiscoveryOptions.Builder()
            .setStrategy(Strategy.P2P_CLUSTER)
            .build()

        connectionClient.startDiscovery(
            DISCOVERY_SERVICE_ID,
            endpointDiscoveryCallback,
            discoveryOptions
        ).addOnSuccessListener {
            updateNotification(true, 0)
        }.addOnFailureListener {
            updateNotification(getString(R.string.discovery_nearby_failed))
        }

    }

    private fun stopDiscovery() {
        connectionClient.stopDiscovery()
    }

    private fun updateNotification(isDiscovery: Boolean, scannedDevicesCount: Int) {
        notificationManager.notify(
            FeinnNotification.NOTIFICATION_ID_DISCOVERY,
            createNotification(isDiscovery, scannedDevicesCount)
        )
    }

    private fun updateNotification(content: CharSequence) {
        notificationManager.notify(
            FeinnNotification.NOTIFICATION_ID_DISCOVERY,
            createNotification(content, R.drawable.ic_nearby_off)
        )
    }

    private fun createNotification(isDiscovery: Boolean, scannedDevicesCount: Int): Notification {
        var icon = R.drawable.ic_nearby_off
        var content = getString(R.string.discovery_nearby_not_discovery)
        if (isDiscovery) {
            icon = R.drawable.ic_nearby
            if (scannedDevicesCount == 1) {
                content = getString(R.string.discovery_nearby_single_device)
            } else {
                content = getString(R.string.discovery_nearby_multiple_devices, scannedDevicesCount)
            }
        }

        return createNotification(content, icon)
    }

    private fun createNotification(content: CharSequence, icon: Int): Notification {
        return NotificationCompat
            .Builder(this, FeinnNotification.NOTIFICATION_CHANNEL_ID_DISCOVERY_SERVICE)
            .setContentTitle(getString(R.string.discovery_nearby_notification_channel_name))
            .setContentText(content)
            .setSmallIcon(icon)
            .build()
    }

    inner class DiscoveryNearbyBinder : Binder() {

        fun setListener(listener: DiscoveryNearbyListener?) {
            this@DiscoveryNearbyService.listener = listener
        }

    }

    inner class FeinnEndpointDiscoveryCallback : EndpointDiscoveryCallback() {

        private val discoveredEndpoint: LinkedHashMap<String, NearbyDevice> = linkedMapOf()

        override fun onEndpointFound(
            endpointId: String,
            info: DiscoveredEndpointInfo
        ) {
            val nearbyDevice = NearbyDevice(endpointId, info)
            discoveredEndpoint[endpointId] = nearbyDevice

            listener?.onEndpointFound(nearbyDevice)
            listener?.onAllEndpointFound(discoveredEndpoint.values.toList())

            updateNotification(true, discoveredEndpoint.size)
        }

        override fun onEndpointLost(endpointId: String) {
            discoveredEndpoint.remove(endpointId)

            listener?.onEndpointLost(endpointId)
            listener?.onAllEndpointFound(discoveredEndpoint.values.toList())

            updateNotification(true, discoveredEndpoint.size)
        }

    }

    interface DiscoveryNearbyListener {
        fun onEndpointFound(nearbyDevice: NearbyDevice)
        fun onEndpointLost(endpointId: String)
        fun onAllEndpointFound(nearbyDevices: List<NearbyDevice>)
    }

}