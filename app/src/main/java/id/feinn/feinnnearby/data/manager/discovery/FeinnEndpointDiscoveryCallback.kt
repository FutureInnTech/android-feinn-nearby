package id.feinn.feinnnearby.data.manager.discovery

import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import id.feinn.feinnnearby.model.NearbyDevice

class FeinnEndpointDiscoveryCallback : EndpointDiscoveryCallback() {
    private var listener: DiscoveryNearbyListener? = null

    fun setListener(l: DiscoveryNearbyListener) {
        listener = l
    }

    override fun onEndpointFound(
        endpointId: String,
        info: DiscoveredEndpointInfo
    ) {
        val nearbyDevice = NearbyDevice(endpointId)

        listener?.onEndpointFound(nearbyDevice)
    }

    override fun onEndpointLost(endpointId: String) {
        listener?.onEndpointLost(endpointId)
    }

}