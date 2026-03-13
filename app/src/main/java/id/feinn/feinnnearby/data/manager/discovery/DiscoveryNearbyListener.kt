package id.feinn.feinnnearby.data.manager.discovery

import id.feinn.feinnnearby.model.NearbyDevice

interface DiscoveryNearbyListener {

    fun onEndpointFound(nearbyDevice: NearbyDevice)
    fun onEndpointLost(endpointId: String)
    fun onAllEndpointFound(nearbyDevices: List<NearbyDevice>)

}