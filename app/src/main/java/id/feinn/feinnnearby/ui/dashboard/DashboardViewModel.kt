package id.feinn.feinnnearby.ui.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import id.feinn.feinnnearby.data.manager.discovery.DiscoveryNearbyListener
import id.feinn.feinnnearby.data.service.communication.CommunicationNearbyCommand
import id.feinn.feinnnearby.data.service.communication.CommunicationNearbyManager
import id.feinn.feinnnearby.model.NearbyDevice

class DashboardViewModel(
    private val communicationNearbyManager: CommunicationNearbyManager
) : ViewModel() {

    private val discoveryNearbyListener = object : DiscoveryNearbyListener {

        override fun onDiscoveryStarted() {
            Log.d("DashboardViewModel", "onDiscoveryStarted: Discovery Started")
        }

        override fun onDiscoveryStoped() {
            Log.d("DashboardViewModel", "onDiscoveryStoped: Discovery Stoped")
        }

        override fun onDiscoveryFailed(e: Exception) {
            Log.d("DashboardViewModel", "onDiscoveryFailed: Discovery Failed")
        }

        override fun onEndpointFound(nearbyDevice: NearbyDevice) {
            Log.d("DashboardViewModel", "onEndpointFound: Endpoint Found")
        }

        override fun onEndpointLost(endpointId: String) {
            Log.d("DashboardViewModel", "onEndpointLost: Endpoint Lost")
        }

    }

    init {
        setDiscoveryListener()
    }

    fun onEvent(event: DashboardEvent) {
        when(event) {
            is DashboardEvent.StartDiscovery -> startDiscovery()
            is DashboardEvent.StopDiscovery -> stopDiscovery()
        }
    }

    private fun setDiscoveryListener() {
        communicationNearbyManager.sendCommand(CommunicationNearbyCommand.Discovery.DiscoveryListener(discoveryNearbyListener))
    }

    private fun startDiscovery() {
        communicationNearbyManager.sendCommand(CommunicationNearbyCommand.Discovery.StartDiscovery)
    }

    private fun stopDiscovery() {
        communicationNearbyManager.sendCommand(CommunicationNearbyCommand.Discovery.StopDiscovery)
    }

}

data class DashboardDataState(
    val isDiscovery: Boolean = false
)

sealed interface DashboardEvent {

    data object StartDiscovery: DashboardEvent
    data object StopDiscovery: DashboardEvent

}
