package id.feinn.feinnnearby.model

import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo

data class NearbyDevice(
    val endpointId: String,
    val info: DiscoveredEndpointInfo
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NearbyDevice) return false
        return endpointId == other.endpointId
    }

    override fun hashCode(): Int {
        return endpointId.hashCode()
    }

}
