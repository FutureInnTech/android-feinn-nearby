package id.feinn.feinnnearby.data.manager.discovery

import android.os.Build
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import id.feinn.feinnnearby.data.manager.pairing.PairingManager
import id.feinn.feinnnearby.model.NearbyDevice

class FeinnEndpointDiscoveryCallback(
    private val pairingManager: PairingManager
) : EndpointDiscoveryCallback() {
    private var listener: DiscoveryListener? = null

    fun setListener(l: DiscoveryListener?) {
        listener = l
    }

    override fun onEndpointFound(
        endpointId: String,
        info: DiscoveredEndpointInfo
    ) {
        val nearbyDevice = NearbyDevice(
            endpointId = endpointId,
            endpointName = info.endpointName
        )
        pairingManager.ensureAddDiscoveryResult(
            endpointId = endpointId,
            nearbyDevice = nearbyDevice
        )

        if (Build.MODEL < info.endpointName) {
            pairingManager.requestConnection(endpointId)
        }

        listener?.onEndpointFound(nearbyDevice)
    }

    override fun onEndpointLost(endpointId: String) {
        pairingManager.removeOrUpdateStatus(endpointId)

        listener?.onEndpointLost(endpointId)
    }

}