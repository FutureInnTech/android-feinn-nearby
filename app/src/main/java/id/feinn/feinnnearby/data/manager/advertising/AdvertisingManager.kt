package id.feinn.feinnnearby.data.manager.advertising

import android.content.Context
import android.os.Build
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.Strategy
import id.feinn.feinnnearby.data.manager.pairing.FeinnConnectionLifecycleCallback
import id.feinn.feinnnearby.data.manager.pairing.PairingManager
import id.feinn.feinnnearby.data.service.communication.CommunicationLifecycle
import id.feinn.feinnnearby.utils.FeinnNearby

class AdvertisingManager(
    private val context: Context,
    private val pairingManager: PairingManager
): CommunicationLifecycle {

    private val connectionClient by lazy { Nearby.getConnectionsClient(context) }
    private val connectionLifecycleCallback: FeinnConnectionLifecycleCallback = FeinnConnectionLifecycleCallback(pairingManager)
    private var advertisingListener: AdvertisingListener? = null

    fun startAdvertising() {
        val advertisingOptions = AdvertisingOptions.Builder()
            .setStrategy(Strategy.P2P_CLUSTER)
            .build()

        connectionClient.startAdvertising(
            Build.MODEL, // TODO ganti dari konfigurasi user
            FeinnNearby.NEARBY_SERVICE_ID,
            connectionLifecycleCallback,
            advertisingOptions
        ).addOnSuccessListener {
            Log.d("AdvertisingManager", "startAdvertising: Advertising started")
            pairingManager.updateAdvertisingStatus(true)

            advertisingListener?.onAdvertisingStarted()
        }.addOnFailureListener {
            Log.e("AdvertisingManager", "startAdvertising: advertising failed")
            pairingManager.updateAdvertisingStatus(false)

            advertisingListener?.onAdvertisingFailed(it)
        }
    }

    fun stopAdvertising() {
        connectionClient.stopAdvertising()
        pairingManager.updateAdvertisingStatus(false)
    }

    fun setListener(l: AdvertisingListener) {
        advertisingListener = l
        connectionLifecycleCallback.setListener(advertisingListener!!)
    }

    override fun onCreate() {
        // do nothing
    }

    override fun onDestroy() {
        connectionLifecycleCallback.setListener(null)
        advertisingListener = null
    }

}