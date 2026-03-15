package id.feinn.feinnnearby.data.manager.pairing

import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate

class FeinnPayloadCallback(
    private val pairingManager: PairingManager
) : PayloadCallback() {
    override fun onPayloadReceived(
        endpointId: String,
        payload: Payload
    ) {

    }

    override fun onPayloadTransferUpdate(
        endpointId: String,
        payloadTransferUpdate: PayloadTransferUpdate
    ) {

    }
}