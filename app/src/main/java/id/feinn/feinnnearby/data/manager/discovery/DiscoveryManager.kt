package id.feinn.feinnnearby.data.manager.discovery

import android.content.Context
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.Strategy
import id.feinn.feinnnearby.data.manager.pairing.PairingManager
import id.feinn.feinnnearby.data.service.communication.CommunicationLifecycle
import id.feinn.feinnnearby.utils.FeinnNearby

class DiscoveryManager(
    private val context: Context,
    private val pairingManager: PairingManager
): CommunicationLifecycle {

    private val connectionClient by lazy { Nearby.getConnectionsClient(context) }
    private val endpointDiscoveryCallback: FeinnEndpointDiscoveryCallback = FeinnEndpointDiscoveryCallback(
        pairingManager = pairingManager
    )
    private var discoveryListener: DiscoveryListener? = null

    fun startDiscovery() {
        val discoveryOptions = DiscoveryOptions.Builder()
            .setStrategy(Strategy.P2P_CLUSTER)
            .build()

        connectionClient.startDiscovery(
            FeinnNearby.NEARBY_SERVICE_ID,
            endpointDiscoveryCallback,
            discoveryOptions
        ).addOnSuccessListener {
            Log.d("DiscoveryManager", "startDiscovery: Discovery Started")
            pairingManager.updateDiscoveryStatus(true)

            discoveryListener?.onDiscoveryStarted()
        }.addOnFailureListener {
            Log.e("DiscoveryManager", "startDiscovery: Discovery Failed")
            pairingManager.updateDiscoveryStatus(false)

            discoveryListener?.onDiscoveryFailed(it)
        }

    }

    fun stopDiscovery() {
        connectionClient.stopDiscovery()
        pairingManager.updateDiscoveryStatus(false)
        discoveryListener?.onDiscoveryStopped()
    }

    fun setListener(l: DiscoveryListener) {
        discoveryListener = l
        endpointDiscoveryCallback.setListener(discoveryListener)
    }

    override fun onCreate() {
        // do nothing
    }

    override fun onDestroy() {
        endpointDiscoveryCallback.setListener(null)
        discoveryListener = null
    }


}