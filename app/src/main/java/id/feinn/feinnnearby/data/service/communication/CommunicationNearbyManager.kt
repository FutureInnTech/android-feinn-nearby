package id.feinn.feinnnearby.data.service.communication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder

class CommunicationNearbyManager(
    private val context: Context
) {

    private var binder: CommunicationNearbyBinder? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            binder: IBinder?
        ) {
            this@CommunicationNearbyManager.binder = binder as CommunicationNearbyBinder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            this@CommunicationNearbyManager.binder = null
        }

    }

    fun doBind() {
        if (binder == null) {
            val intent = Intent(context, CommunicationNearbyService::class.java)
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    fun doUnbind() {
        if (binder != null) {
            context.unbindService(serviceConnection)
            binder = null
        }
    }

    fun sendCommand(command: CommunicationNearbyCommand) {
        binder?.sendCommand(command)
    }

}