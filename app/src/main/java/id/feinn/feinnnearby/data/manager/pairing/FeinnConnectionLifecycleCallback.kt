package id.feinn.feinnnearby.data.manager.pairing

import android.util.Log
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes
import id.feinn.feinnnearby.data.manager.advertising.AdvertisingListener
import id.feinn.feinnnearby.model.NearbyDevice

class FeinnConnectionLifecycleCallback(
    private val pairingManager: PairingManager
) : ConnectionLifecycleCallback() {

    private var listener: AdvertisingListener? = null

    override fun onConnectionInitiated(
        endpointId: String,
        connectionInfo: ConnectionInfo
    ) {
        Log.d("PairingManager", "onConnectionInitiated: $endpointId ${connectionInfo.endpointName}")
        val nearbyDevice = NearbyDevice(
            endpointId = endpointId,
            endpointName = connectionInfo.endpointName
        )
        pairingManager.ensureAddDiscoveryResult(
            endpointId = endpointId,
            nearbyDevice = nearbyDevice
        )
        pairingManager.acceptConnection(endpointId)
        pairingManager.updateStatus(
            endpointId = endpointId,
            status = NearbyDevice.DeviceStatus.Connecting
        )

        listener?.onConnectionInitiated(nearbyDevice)
    }

    override fun onConnectionResult(
        endpointId: String,
        connectionResolution: ConnectionResolution
    ) {
        Log.d("PairingManager", "onConnectionResult: $endpointId ${connectionResolution.status.statusCode}")
        when (connectionResolution.status.statusCode) {

            ConnectionsStatusCodes.STATUS_OK, ConnectionsStatusCodes.STATUS_ALREADY_CONNECTED_TO_ENDPOINT -> {
                pairingManager.updateStatus(
                    endpointId = endpointId,
                    status = NearbyDevice.DeviceStatus.Connected
                )
            }

            ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {
                pairingManager.updateStatus(
                    endpointId = endpointId,
                    status = NearbyDevice.DeviceStatus.Rejected
                )
            }

            else -> {
                pairingManager.updateStatus(
                    endpointId = endpointId,
                    status = NearbyDevice.DeviceStatus.Failed
                )
            }
        }
    }

    override fun onDisconnected(endpointId: String) {
        pairingManager.updateStatus(
            endpointId = endpointId,
            status = NearbyDevice.DeviceStatus.Disconnected
        )
    }

    fun setListener(l: AdvertisingListener?) {
        listener = l
    }

}