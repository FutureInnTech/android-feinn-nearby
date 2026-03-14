package id.feinn.feinnnearby.data.service.communication

import android.os.Binder

class CommunicationNearbyBinder(
    private val communicationNearbyService: CommunicationNearbyService
) : Binder() {

    fun sendCommand(command: CommunicationNearbyCommand) {
        communicationNearbyService.handleCommand(
            command = command
        )
    }

}