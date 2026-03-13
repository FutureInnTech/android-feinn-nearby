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
    private val endpointDiscoveryCallback: FeinnEndpointDiscoveryCallback =
        FeinnEndpointDiscoveryCallback()

    fun startDiscovery() {
        val discoveryOptions = DiscoveryOptions.Builder()
            .setStrategy(Strategy.P2P_CLUSTER)
            .build()

        connectionClient.startDiscovery(
            FeinnNearby.NEARBY_SERVICE_ID,
            endpointDiscoveryCallback,
            discoveryOptions
        ).addOnSuccessListener {
            Log.d("DiscoveryManager", "Discovery Started")
        }.addOnFailureListener {
            Log.e("DiscoveryManager", "Discovery Failed")
        }

    }

    fun stopDiscovery() {
        connectionClient.stopDiscovery()
    }

    fun setListener(l: DiscoveryNearbyListener) {
        endpointDiscoveryCallback.setListener(l)
    }


}