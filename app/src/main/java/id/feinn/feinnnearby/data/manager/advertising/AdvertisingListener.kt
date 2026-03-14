package id.feinn.feinnnearby.data.manager.advertising

import id.feinn.feinnnearby.model.NearbyDevice

interface AdvertisingListener {

    fun onAdvertisingStarted()
    fun onAdvertisingStopped()
    fun onAdvertisingFailed(e: Exception)
    fun onConnectionInitiated(nearbyDevice: NearbyDevice)
    fun onConnectionResult(nearbyDevice: NearbyDevice)
    fun onDisconnected(nearbyDevice: NearbyDevice)


}