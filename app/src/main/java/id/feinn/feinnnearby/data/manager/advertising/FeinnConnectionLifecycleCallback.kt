package id.feinn.feinnnearby.data.manager.advertising

import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import id.feinn.feinnnearby.model.NearbyDevice

class FeinnConnectionLifecycleCallback : ConnectionLifecycleCallback() {

    private var listener: AdvertisingListener? = null

    override fun onConnectionInitiated(
        endpointId: String,
        connectionInfo: ConnectionInfo
    ) {
        val nearbyDevice = NearbyDevice(endpointId)

        listener?.onConnectionInitiated(nearbyDevice)
    }

    override fun onConnectionResult(
        endpointId: String,
        connectionResolution: ConnectionResolution
    ) {
        val nearbyDevice = NearbyDevice(endpointId)

        listener?.onConnectionResult(nearbyDevice)
    }

    override fun onDisconnected(endpointId: String) {
        val nearbyDevice = NearbyDevice(endpointId)

        listener?.onDisconnected(nearbyDevice)
    }

    fun setListener(l: AdvertisingListener) {
        listener = l
    }

}