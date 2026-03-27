package id.feinn.feinnnearby.data.manager.pairing

import android.content.Context
import android.os.Build
import android.util.Log
import com.google.android.gms.nearby.Nearby
import id.feinn.feinnnearby.FeinnException
import id.feinn.feinnnearby.R
import id.feinn.feinnnearby.data.service.communication.CommunicationLifecycle
import id.feinn.feinnnearby.model.NearbyDevice
import id.feinn.feinnnearby.utils.FeinnNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
    private val nearbyResults: MutableStateFlow<HashMap<String, NearbyDevice>> = MutableStateFlow(hashMapOf())
    private var jobNearbyResult: Job? = null
    private val payloadCallback: FeinnPayloadCallback = FeinnPayloadCallback(this)
    private val connectionLifecycleCallback: FeinnConnectionLifecycleCallback = FeinnConnectionLifecycleCallback(this)
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val isDiscoveryActive: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isAdvertisingActive: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isCommunicationActive = isDiscoveryActive.combine(isAdvertisingActive) { discovery, advertising ->
        discovery && advertising
    }

    fun ensureAddDiscoveryResult(endpointId: String, nearbyDevice: NearbyDevice) {
        nearbyResults.value[endpointId] = nearbyDevice
    }

    @Throws(FeinnException::class)
    fun removeOrUpdateStatus(endpointId: String) {
        val device = nearbyResults.value[endpointId] ?: throw FeinnException("endpointId $endpointId not found")

        if (device.status == NearbyDevice.DeviceStatus.Discovered) {
            nearbyResults.value.remove(endpointId)
            return
        }

        device.status = NearbyDevice.DeviceStatus.Lost
    }

    fun removeAllDiscoveryResult() {
        nearbyResults.value.clear()
    }

    @Throws(FeinnException::class)
    fun updateStatus(endpointId: String, status: NearbyDevice.DeviceStatus) {
        val device = nearbyResults.value[endpointId] ?: throw FeinnException("endpointId $endpointId not found")

        device.status = status

        Log.d("PairingManager", "updateStatus: ${device.status} ${device.endpointId} ${device.endpointName}")
    }

    @Throws(FeinnException::class)
    fun acceptConnection(endpointId: String) {
        val device = nearbyResults.value[endpointId] ?: throw FeinnException("endpointId $endpointId not found")

        connectionClient.acceptConnection(endpointId, payloadCallback)

        Log.d("PairingManager", "acceptConnection: ${device.status} ${device.endpointId} ${device.endpointName}")
    }

    @Throws(FeinnException::class)
    fun requestConnection(endpointId: String) {
        val device = nearbyResults.value[endpointId] ?: throw FeinnException("endpointId $endpointId not found")

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
            if (status) {
                updateNotificationCommunication()
            } else {
                destroyJobUpdateNotification()
                FeinnNotification.updateCommunicationNearbyService(
                    context,
                    message = context.getString(R.string.communication_service_off),
                    icon = R.drawable.ic_launcher_foreground,
                )
            }
        }
    }

    private fun updateNotificationCommunication() {
        jobNearbyResult = coroutineScope.launch {
            nearbyResults.collectLatest { nearbyResult ->
                val message = context.getString(R.string.communication_nearby_number_of_connected, nearbyResult.size)

                FeinnNotification.updateCommunicationNearbyService(
                    context,
                    message = message,
                    icon = R.drawable.ic_launcher_foreground
                )
            }
        }
    }

    private fun destroyJobUpdateNotification() {
        jobNearbyResult?.cancel()
        jobNearbyResult = null
    }

    override fun onCreate() {
        coroutineScope.launch {
            collectStatusCommunication()
        }
    }

    override fun onDestroy() {
        removeAllDiscoveryResult()
        destroyJobUpdateNotification()
        coroutineScope.cancel()
    }

}