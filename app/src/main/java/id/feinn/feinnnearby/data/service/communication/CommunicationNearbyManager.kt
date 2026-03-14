package id.feinn.feinnnearby.data.service.communication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.Executors

class CommunicationNearbyManager(
    private val context: Context
) {

    private var binder: CommunicationNearbyBinder? = null

    private val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    private val scope = CoroutineScope(dispatcher + SupervisorJob())
    private val mutex: Mutex = Mutex()
    private val pendingCommand = ArrayDeque<CommunicationNearbyCommand>()

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            binder: IBinder?
        ) {
            scope.launch {
                mutex.withLock {
                    Log.d("CommunicationNearbyManager", "onServiceConnected: binder connected")
                    this@CommunicationNearbyManager.binder = binder as CommunicationNearbyBinder
                    flushCommand()
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            scope.launch {
                mutex.withLock {
                    Log.d("CommunicationNearbyManager", "onServiceDisconnected: binder disconnected")
                    this@CommunicationNearbyManager.binder = null
                }
            }
        }

    }

    fun ensureBind() {
        scope.launch {
            mutex.withLock {
                if (binder == null) {
                    val intent = Intent(context, CommunicationNearbyService::class.java)
                    context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
                }
            }
        }
    }

    fun doUnbind() {
        scope.launch {
            mutex.withLock {
                if (binder != null) {
                    context.unbindService(serviceConnection)
                    binder = null
                }
            }
        }
    }

    fun sendCommand(command: CommunicationNearbyCommand) {
        scope.launch {
            mutex.withLock {
                if (binder != null) binder!!.sendCommand(command)

                pendingCommand.addLast(command)
                ensureBind()
            }
        }
    }

    private fun flushCommand() {
        if (binder == null) return

        while (pendingCommand.isNotEmpty()) {
            val command = pendingCommand.removeFirst()
            binder?.sendCommand(command)
        }
    }

}