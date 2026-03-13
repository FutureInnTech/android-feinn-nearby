package id.feinn.feinnnearby.data.service.communication

import android.os.Binder
import id.feinn.feinnnearby.data.manager.discovery.DiscoveryNearbyListener

class CommunicationNearbyBinder(
    private val communicationNearbyService: CommunicationNearbyService
) : Binder() {

    fun sendCommand(command: CommunicationNearbyCommand) {
        communicationNearbyService.handleCommand(
            command = command
        )
    }

    fun setListenerDiscovery(listener: DiscoveryNearbyListener) {
        communicationNearbyService.setListenerDiscovery(
            listener = listener
        )
    }

}