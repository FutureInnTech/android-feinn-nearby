package id.feinn.feinnnearby.data.manager.pairing

import android.content.Context
import android.os.Build
import android.util.Log
import com.google.android.gms.nearby.Nearby
import id.feinn.feinnnearby.FeinnException
import id.feinn.feinnnearby.model.NearbyDevice

class PairingManager(
    private val context: Context
) {

    private val connectionClient by lazy { Nearby.getConnectionsClient(context) }
    private val nearbyResult: HashMap<String, NearbyDevice> = hashMapOf()
    private val payloadCallback: FeinnPayloadCallback = FeinnPayloadCallback(this)
    private val connectionLifecycleCallback: FeinnConnectionLifecycleCallback = FeinnConnectionLifecycleCallback(this)

    fun ensureAddDiscoveryResult(endpointId: String, nearbyDevice: NearbyDevice) {
        nearbyResult[endpointId] = nearbyDevice
    }

    @Throws(FeinnException::class)
    fun removeOrUpdateStatus(endpointId: String) {
        if (nearbyResult[endpointId] == null) throw FeinnException("endpointId $endpointId not found")

        if (nearbyResult[endpointId]!!.status == NearbyDevice.DeviceStatus.Discovered) {
            nearbyResult.remove(endpointId)
            return
        }

        nearbyResult[endpointId]!!.status = NearbyDevice.DeviceStatus.Lost
    }

    fun removeAllDiscoveryResult() {
        nearbyResult.clear()
    }

    @Throws(FeinnException::class)
    fun updateStatus(endpointId: String, status: NearbyDevice.DeviceStatus) {
        if (nearbyResult[endpointId] == null) throw FeinnException("endpointId $endpointId not found")

        nearbyResult[endpointId]!!.status = status

        Log.d("PairingManager", "updateStatus: ${nearbyResult[endpointId]!!.status} ${nearbyResult[endpointId]!!.endpointId} ${nearbyResult[endpointId]!!.endpointName}")
    }

    @Throws(FeinnException::class)
    fun acceptConnection(endpointId: String) {
        if (nearbyResult[endpointId] == null) throw FeinnException("endpointId $endpointId not found")

        connectionClient.acceptConnection(endpointId, payloadCallback)

        Log.d("PairingManager", "acceptConnection: ${nearbyResult[endpointId]!!.status} ${nearbyResult[endpointId]!!.endpointId} ${nearbyResult[endpointId]!!.endpointName}")
    }

    @Throws(FeinnException::class)
    fun requestConnection(endpointId: String) {
        if (nearbyResult[endpointId] == null) throw FeinnException("endpointId $endpointId not found")

        connectionClient.requestConnection(
            Build.MODEL, // TODO change with address
            endpointId,
            connectionLifecycleCallback
        )

        Log.d("PairingManager", "requestConnection: ${nearbyResult[endpointId]!!.endpointName} ${nearbyResult[endpointId]!!.endpointId} ${nearbyResult[endpointId]!!.status}")
    }



}