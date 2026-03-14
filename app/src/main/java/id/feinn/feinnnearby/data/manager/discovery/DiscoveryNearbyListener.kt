package id.feinn.feinnnearby.data.manager.discovery

import id.feinn.feinnnearby.model.NearbyDevice

interface DiscoveryNearbyListener {

    fun onDiscoveryStarted()
    fun onDiscoveryStoped()
    fun onDiscoveryFailed(e: Exception)
    fun onEndpointFound(nearbyDevice: NearbyDevice)
    fun onEndpointLost(endpointId: String)

}