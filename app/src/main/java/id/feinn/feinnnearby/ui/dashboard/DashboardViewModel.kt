package id.feinn.feinnnearby.ui.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import id.feinn.feinnnearby.data.manager.advertising.AdvertisingListener
import id.feinn.feinnnearby.data.manager.discovery.DiscoveryListener
import id.feinn.feinnnearby.data.service.communication.CommunicationNearbyCommand
import id.feinn.feinnnearby.data.service.communication.CommunicationNearbyManager
import id.feinn.feinnnearby.model.NearbyDevice

class DashboardViewModel(
    private val communicationNearbyManager: CommunicationNearbyManager
) : ViewModel() {

    private val discoveryListener = object : DiscoveryListener {

        override fun onDiscoveryStarted() {
            Log.d("DashboardViewModel", "onDiscoveryStarted: Discovery Started")
        }

        override fun onDiscoveryStopped() {
            Log.d("DashboardViewModel", "onDiscoveryStoped: Discovery Stoped")
        }

        override fun onDiscoveryFailed(e: Exception) {
            Log.e("DashboardViewModel", "onDiscoveryFailed: Discovery Failed")
        }

        override fun onEndpointFound(nearbyDevice: NearbyDevice) {
            Log.d("DashboardViewModel", "onEndpointFound: Endpoint Found")
        }

        override fun onEndpointLost(endpointId: String) {
            Log.d("DashboardViewModel", "onEndpointLost: Endpoint Lost")
        }

    }

    private val advertisingListener = object : AdvertisingListener {
        override fun onAdvertisingStarted() {
            Log.d("DashboardViewModel", "onAdvertisingStarted: Advertising Started")
        }

        override fun onAdvertisingStopped() {
            Log.d("DashboardViewModel", "onAdvertisingStoped: Advertising Stoped")
        }

        override fun onAdvertisingFailed(e: Exception) {
            Log.e("DashboardViewModel", "onAdvertisingFailed: Advertising Failed")
        }

        override fun onConnectionInitiated(nearbyDevice: NearbyDevice) {
            Log.d("DashboardViewModel", "onConnectionInitiated: Connection Initiated")
        }

        override fun onConnectionResult(nearbyDevice: NearbyDevice) {
            Log.d("DashboardViewModel", "onConnectionResult: Connection Result")
        }

        override fun onDisconnected(nearbyDevice: NearbyDevice) {
            Log.d("DashboardViewModel", "onDisconnected: Disconnected")
        }

    }

    init {
        setBroadcastListener()
    }

    fun onEvent(event: DashboardEvent) {
        when(event) {
            is DashboardEvent.StartBroadcast -> startBroadcast()
            is DashboardEvent.StopBroadcast -> stopBroadcast()
        }
    }

    private fun setBroadcastListener() {
        communicationNearbyManager.sendCommand(CommunicationNearbyCommand.Discovery.DiscoveryListenerCommand(discoveryListener))
        communicationNearbyManager.sendCommand(CommunicationNearbyCommand.Advertising.AdvertisingListenerCommand(advertisingListener))
    }

    private fun startBroadcast() {
        communicationNearbyManager.sendCommand(CommunicationNearbyCommand.Discovery.StartDiscoveryCommand)
        communicationNearbyManager.sendCommand(CommunicationNearbyCommand.Advertising.StartAdvertisingCommand)
    }

    private fun stopBroadcast() {
        communicationNearbyManager.sendCommand(CommunicationNearbyCommand.Discovery.StopDiscoveryCommand)
        communicationNearbyManager.sendCommand(CommunicationNearbyCommand.Advertising.StopAdvertisingCommand)
    }

}

data class DashboardDataState(
    val isDiscovery: Boolean = false
)

sealed interface DashboardEvent {

    data object StartBroadcast: DashboardEvent
    data object StopBroadcast: DashboardEvent

}
