package id.feinn.feinnnearby.data.service.communication

sealed interface CommunicationNearbyCommand {

    sealed interface Discovery: CommunicationNearbyCommand {
        data object StartDiscovery: Discovery
        data object StopDiscovery: Discovery
    }

    sealed interface Advertising: CommunicationNearbyCommand {
        data object StartAdvertising: Advertising
        data object StopAdvertising: Advertising

    }

}