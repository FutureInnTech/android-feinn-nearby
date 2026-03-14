package id.feinn.feinnnearby.data.manager.discovery

import android.content.Context
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.Strategy
import id.feinn.feinnnearby.utils.FeinnNearby

class DiscoveryManager(
    private val context: Context
) {

    private val connectionClient by lazy { Nearby.getConnectionsClient(context) }
    private val endpointDiscoveryCallback: FeinnEndpointDiscoveryCallback = FeinnEndpointDiscoveryCallback()
    private var discoveryNearbyListener: DiscoveryNearbyListener? = null

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
            discoveryNearbyListener?.onDiscoveryStarted()
        }.addOnFailureListener {
            Log.e("DiscoveryManager", "startDiscovery: Discovery Failed")
            discoveryNearbyListener?.onDiscoveryFailed(it)
        }

    }

    fun stopDiscovery() {
        connectionClient.stopDiscovery()
        discoveryNearbyListener?.onDiscoveryStoped()
    }

    fun setListener(l: DiscoveryNearbyListener) {
        discoveryNearbyListener = l
        endpointDiscoveryCallback.setListener(discoveryNearbyListener!!)
    }


}