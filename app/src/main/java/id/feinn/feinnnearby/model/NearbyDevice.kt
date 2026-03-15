package id.feinn.feinnnearby.model

data class NearbyDevice(
    val endpointId: String,
    val endpointName: String,
    var status: DeviceStatus = DeviceStatus.Discovered
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NearbyDevice) return false
        return endpointId == other.endpointId
    }

    override fun hashCode(): Int {
        return endpointId.hashCode()
    }

    enum class DeviceStatus {
        Discovered,
        Connecting,
        Connected,
        Rejected,
        Disconnected,
        Failed,
        Lost
    }

}
