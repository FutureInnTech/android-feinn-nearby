package id.feinn.feinnnearby.data.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.Strategy
import id.feinn.feinnnearby.utils.FeinnNearby

class AdvertisingNearbyService : Service() {

    companion object {

        const val START_ADVERTISING = "id.feinn.feinnnearby.nearby.service.start_advertising"
        const val STOP_ADVERTISING = "id.feinn.feinnnearby.nearby.service.stop_advertising"

    }

    private val advertisingNearbyBinder: AdvertisingNearbyBinder = AdvertisingNearbyBinder()
    private var listener: AdvertisingNearbyListener? = null
    private val connectionClient by lazy { Nearby.getConnectionsClient(this) }
    private val connectionLifecycleCallback: FeinnAdvertisingCallback =
        FeinnAdvertisingCallback()

    override fun onBind(intent: Intent?): IBinder? {
        return advertisingNearbyBinder
    }

    override fun onCreate() {
        super.onCreate()
    }

    @Synchronized
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action != null) handleAction(intent)
        // TODO Handle action null ketika service dijalankan ulang karena out of memory atau alasan lain

        return START_STICKY
    }

    private fun handleAction(intent: Intent) {
        when (intent.action) {
            START_ADVERTISING -> {
                startAdvertising()
            }
            STOP_ADVERTISING -> {
                stopAdvertising()
            }
        }
    }

    private fun startAdvertising() {
        val advertisingOptions = AdvertisingOptions.Builder()
            .setStrategy(Strategy.P2P_CLUSTER)
            .build()

        connectionClient.startAdvertising(
            Build.MODEL, // TODO ganti dari konfigurasi user
            FeinnNearby.NEARBY_SERVICE_ID,
            connectionLifecycleCallback,
            advertisingOptions
        ).addOnSuccessListener {
            Log.d("NearbyService", "Advertising dimulai")
        }.addOnFailureListener {
            Log.e("NearbyService", "Gagal memulai advertising: ${it.message}")
        }
    }

    private fun stopAdvertising() {
        connectionClient.stopAdvertising()
    }

    private inner class FeinnAdvertisingCallback : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(
            endpointId: String,
            connectionInfo: ConnectionInfo
        ) {
            Log.d("NearbyService", "Koneksi diminta oleh ${connectionInfo.endpointName}")
        }

        override fun onConnectionResult(
            endpointId: String,
            connectionResolution: ConnectionResolution
        ) {
            Log.d("NearbyService", "Hasil koneksi dari endpoint $endpointId: ${connectionResolution.status}")
        }

        override fun onDisconnected(endpointId: String) {
            Log.d("NearbyService", "Terputus dari endpoint $endpointId")
        }
    }

    private inner class AdvertisingNearbyBinder : Binder() {

        fun setListener(listener: AdvertisingNearbyListener) {
            this@AdvertisingNearbyService.listener = listener
        }

    }

    interface AdvertisingNearbyListener {
        fun onAdvertisingStarted()
        fun onAdvertisingStopped()
    }

}