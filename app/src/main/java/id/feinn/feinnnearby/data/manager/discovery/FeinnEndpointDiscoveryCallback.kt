package id.feinn.feinnnearby.data.manager.discovery

import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import id.feinn.feinnnearby.model.NearbyDevice

class FeinnEndpointDiscoveryCallback : EndpointDiscoveryCallback() {

    private val discoveredEndpoint: LinkedHashMap<String, NearbyDevice> = linkedMapOf()
    private var listener: DiscoveryNearbyListener? = null

    fun setListener(l: DiscoveryNearbyListener) {
        listener = l
    }

    override fun onEndpointFound(
        endpointId: String,
        info: DiscoveredEndpointInfo
    ) {
        val nearbyDevice = NearbyDevice(endpointId)
        discoveredEndpoint[endpointId] = nearbyDevice

        listener?.onEndpointFound(nearbyDevice)
        listener?.onAllEndpointFound(discoveredEndpoint.values.toList())
    }

    override fun onEndpointLost(endpointId: String) {
        discoveredEndpoint.remove(endpointId)

        listener?.onEndpointLost(endpointId)
        listener?.onAllEndpointFound(discoveredEndpoint.values.toList())
    }

}