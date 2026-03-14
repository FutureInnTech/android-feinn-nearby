package id.feinn.feinnnearby.ui.dashboard

import androidx.lifecycle.ViewModel
import id.feinn.feinnnearby.data.service.communication.CommunicationNearbyCommand
import id.feinn.feinnnearby.data.service.communication.CommunicationNearbyManager

class DashboardViewModel(
    private val communicationNearbyManager: CommunicationNearbyManager
) : ViewModel() {

    fun onEvent(event: DashboardEvent) {
        when(event) {
            is DashboardEvent.StartDiscovery -> {
                communicationNearbyManager.sendCommand(CommunicationNearbyCommand.Discovery.StartDiscovery)
            }
            is DashboardEvent.StopDiscovery -> {
                communicationNearbyManager.sendCommand(CommunicationNearbyCommand.Discovery.StopDiscovery)
            }
        }
    }

}

data class DashboardDataState(
    val isDiscovery: Boolean = false
)

sealed interface DashboardEvent {

    data object StartDiscovery: DashboardEvent
    data object StopDiscovery: DashboardEvent

}
