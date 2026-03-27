package id.feinn.feinnnearby.data.manager.pairing

import android.content.Context
import android.os.Build
import android.util.Log
import com.google.android.gms.nearby.Nearby
import id.feinn.feinnnearby.FeinnException
import id.feinn.feinnnearby.data.service.communication.CommunicationLifecycle
import id.feinn.feinnnearby.model.NearbyDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PairingManager(
    private val context: Context
) : CommunicationLifecycle {

    private val connectionClient by lazy { Nearby.getConnectionsClient(context) }
    private val nearbyResult: HashMap<String, NearbyDevice> = hashMapOf()
    private val payloadCallback: FeinnPayloadCallback = FeinnPayloadCallback(this)
    private val connectionLifecycleCallback: FeinnConnectionLifecycleCallback = FeinnConnectionLifecycleCallback(this)
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val isDiscoveryActive: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isAdvertisingActive: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isCommunicationActive = isDiscoveryActive.combine(isAdvertisingActive) { discovery, advertising ->
        discovery && advertising
    }

    fun ensureAddDiscoveryResult(endpointId: String, nearbyDevice: NearbyDevice) {
        nearbyResult[endpointId] = nearbyDevice
    }

    @Throws(FeinnException::class)
    fun removeOrUpdateStatus(endpointId: String) {
        val device = nearbyResult[endpointId] ?: throw FeinnException("endpointId $endpointId not found")

        if (device.status == NearbyDevice.DeviceStatus.Discovered) {
            nearbyResult.remove(endpointId)
            return
        }

        device.status = NearbyDevice.DeviceStatus.Lost
    }

    fun removeAllDiscoveryResult() {
        nearbyResult.clear()
    }

    @Throws(FeinnException::class)
    fun updateStatus(endpointId: String, status: NearbyDevice.DeviceStatus) {
        val device = nearbyResult[endpointId] ?: throw FeinnException("endpointId $endpointId not found")

        device.status = status

        Log.d("PairingManager", "updateStatus: ${device.status} ${device.endpointId} ${device.endpointName}")
    }

    @Throws(FeinnException::class)
    fun acceptConnection(endpointId: String) {
        val device = nearbyResult[endpointId] ?: throw FeinnException("endpointId $endpointId not found")

        connectionClient.acceptConnection(endpointId, payloadCallback)

        Log.d("PairingManager", "acceptConnection: ${device.status} ${device.endpointId} ${device.endpointName}")
    }

    @Throws(FeinnException::class)
    fun requestConnection(endpointId: String) {
        val device = nearbyResult[endpointId] ?: throw FeinnException("endpointId $endpointId not found")

        connectionClient.requestConnection(
            Build.MODEL, // TODO change with address
            endpointId,
            connectionLifecycleCallback
        )

        Log.d("PairingManager", "requestConnection: ${device.endpointName} ${device.endpointId} ${device.status}")
    }

    fun updateDiscoveryStatus(status: Boolean) {
        isDiscoveryActive.value = status
    }

    fun updateAdvertisingStatus(status: Boolean) {
        isAdvertisingActive.value = status
    }

    private suspend fun collectStatusCommunication() {
        isCommunicationActive.collectLatest { status ->
            Log.d("PairingManager", "collectStatusCommunication: $status")
            // TODO status false show notifiaction not discovery and not advertising others show number of connected devices
        }
    }

    override fun onCreate() {
        coroutineScope.launch {
            collectStatusCommunication()
        }
    }

    override fun onDestroy() {
        removeAllDiscoveryResult()
        coroutineScope.cancel()
    }

}