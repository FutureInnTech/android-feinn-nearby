package id.feinn.feinnnearby.data.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.google.android.gms.nearby.Nearby

class ConnectionNearbyService : Service() {

    companion object {

        const val START_CONNECTION = "id.feinn.feinnnearby.nearby.service.start_connection"
        const val STOP_CONNECTION = "id.feinn.feinnnearby.nearby.service.stop_connection"

    }

    private val connectionNearbyBinder: ConnectionNearbyBinder = ConnectionNearbyBinder()
    private var listener: ConnectionNearbyListener? = null
    private val connectionClient by lazy { Nearby.getConnectionsClient(this) }

    override fun onBind(intent: Intent?): IBinder? {
        return connectionNearbyBinder
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
            START_CONNECTION -> {

            }

            STOP_CONNECTION -> {

            }
        }
    }

    private inner class ConnectionNearbyBinder : Binder() {
        fun getService(): ConnectionNearbyService = this@ConnectionNearbyService
    }

    interface ConnectionNearbyListener {
        fun onConnectionEstablished()
        fun onConnectionFailed()
    }
}