package id.feinn.feinnnearby.data.manager.discovery

import id.feinn.feinnnearby.model.NearbyDevice

interface DiscoveryListener {

    fun onDiscoveryStarted()
    fun onDiscoveryStopped()
    fun onDiscoveryFailed(e: Exception)
    fun onEndpointFound(nearbyDevice: NearbyDevice)
    fun onEndpointLost(endpointId: String)

}