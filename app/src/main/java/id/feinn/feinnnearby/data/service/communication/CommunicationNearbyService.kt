package id.feinn.feinnnearby.data.service.communication

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.ServiceCompat
import id.feinn.feinnnearby.R
import id.feinn.feinnnearby.data.manager.advertising.AdvertisingManager
import id.feinn.feinnnearby.data.manager.discovery.DiscoveryManager
import id.feinn.feinnnearby.data.manager.pairing.PairingManager
import id.feinn.feinnnearby.utils.FeinnNotification

class CommunicationNearbyService : Service() {

    private lateinit var pairingManager: PairingManager
    private lateinit var discoveryManager: DiscoveryManager
    private lateinit var advertisingManager: AdvertisingManager

    private val communicationNearbyBinder = CommunicationNearbyBinder(
        communicationNearbyService = this
    )

    override fun onBind(intent: Intent?): IBinder {
        return communicationNearbyBinder
    }

    override fun onCreate() {
        super.onCreate()
        startForeground()

        pairingManager = PairingManager(this)
        discoveryManager = DiscoveryManager(this, pairingManager)
        advertisingManager = AdvertisingManager(this, pairingManager)

        pairingManager.onCreate()
        discoveryManager.onCreate()
        advertisingManager.onCreate()
    }

    @Synchronized
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun startForeground() {
        val serviceNotification = FeinnNotification.createNotification(
            context = this,
            channelId = FeinnNotification.NOTIFICATION_CHANNEL_ID_COMMUNICATION_NEARBY_SERVICE,
            title = getString(R.string.app_name),
            content = getString(R.string.communication_service_off),
            icon = R.drawable.ic_launcher_foreground
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ServiceCompat.startForeground(
                this,
                FeinnNotification.NOTIFICATION_ID_COMMUNICATION_NEARBY,
                serviceNotification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE
            )
        } else {
            ServiceCompat.startForeground(this, FeinnNotification.NOTIFICATION_ID_COMMUNICATION_NEARBY, serviceNotification, 0)
        }
    }

    fun handleCommand(command: CommunicationNearbyCommand) {
        Log.d("CommunicationNearbyService", "handleCommand: command for ${command.javaClass.simpleName}")
        when (command) {
            is CommunicationNearbyCommand.Discovery -> handleDiscoveryCommand(command)
            is CommunicationNearbyCommand.Advertising -> handleAdvertisingCommand(command)
            is CommunicationNearbyCommand.StartCommunicationCommand -> handleStartCommunicationCommand()
            is CommunicationNearbyCommand.StopCommunicationCommand -> handleStopCommunicationCommand()
        }
    }

    private fun handleStartCommunicationCommand() {
        discoveryManager.startDiscovery()
        advertisingManager.startAdvertising()
    }

    private fun handleStopCommunicationCommand() {
        discoveryManager.stopDiscovery()
        advertisingManager.stopAdvertising()
    }

    private fun handleDiscoveryCommand(command: CommunicationNearbyCommand.Discovery) {
        when (command) {
            CommunicationNearbyCommand.Discovery.StartDiscoveryCommand -> {
                discoveryManager.startDiscovery()
            }
            CommunicationNearbyCommand.Discovery.StopDiscoveryCommand -> {
                discoveryManager.stopDiscovery()
            }
            is CommunicationNearbyCommand.Discovery.DiscoveryListenerCommand -> {
                discoveryManager.setListener(command.listener)
            }
        }
    }

    private fun handleAdvertisingCommand(command: CommunicationNearbyCommand.Advertising) {
        when (command) {
            CommunicationNearbyCommand.Advertising.StartAdvertisingCommand -> {
                advertisingManager.startAdvertising()
            }
            CommunicationNearbyCommand.Advertising.StopAdvertisingCommand -> {
                advertisingManager.stopAdvertising()
            }
            is CommunicationNearbyCommand.Advertising.AdvertisingListenerCommand -> {
                advertisingManager.setListener(command.listener)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pairingManager.onDestroy()
        discoveryManager.onDestroy()
        advertisingManager.onDestroy()

        stopForeground(STOP_FOREGROUND_REMOVE)
    }

}