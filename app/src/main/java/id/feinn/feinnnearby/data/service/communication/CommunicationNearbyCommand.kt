package id.feinn.feinnnearby.data.service.communication

import id.feinn.feinnnearby.data.manager.advertising.AdvertisingListener
import id.feinn.feinnnearby.data.manager.discovery.DiscoveryListener

sealed interface CommunicationNearbyCommand {

    sealed interface Discovery: CommunicationNearbyCommand {
        data object StartDiscoveryCommand: Discovery
        data object StopDiscoveryCommand: Discovery
        data class DiscoveryListenerCommand(val listener: DiscoveryListener): Discovery

    }

    sealed interface Advertising: CommunicationNearbyCommand {
        data object StartAdvertisingCommand: Advertising
        data object StopAdvertisingCommand: Advertising
        data class AdvertisingListenerCommand(val listener: AdvertisingListener): Advertising

    }

}