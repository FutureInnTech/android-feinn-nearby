package id.feinn.feinnnearby.data.service.communication

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.ServiceCompat
import id.feinn.feinnnearby.R
import id.feinn.feinnnearby.data.manager.discovery.DiscoveryManager
import id.feinn.feinnnearby.data.manager.discovery.DiscoveryNearbyListener
import id.feinn.feinnnearby.utils.FeinnNotification

class CommunicationNearbyService : Service() {

    private lateinit var discoveryManager: DiscoveryManager

    private val communicationNearbyBinder = CommunicationNearbyBinder(
        communicationNearbyService = this
    )

    override fun onBind(intent: Intent?): IBinder {
        return communicationNearbyBinder
    }

    override fun onCreate() {
        super.onCreate()
        startForeground()

        discoveryManager = DiscoveryManager(this)
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
            content = getString(R.string.communication_service_started),
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
        when (command) {
            is CommunicationNearbyCommand.Discovery -> handleDiscoveryCommand(command)
            is CommunicationNearbyCommand.Advertising -> handleAdvertisingCommand(command)
        }
    }

    fun setListenerDiscovery(listener: DiscoveryNearbyListener) {
        discoveryManager.setListener(listener)
    }

    private fun handleDiscoveryCommand(command: CommunicationNearbyCommand.Discovery) {
        when (command) {
            is CommunicationNearbyCommand.Discovery.StartDiscovery -> {
                discoveryManager.startDiscovery()
            }
            is CommunicationNearbyCommand.Discovery.StopDiscovery -> {
                discoveryManager.stopDiscovery()
            }
        }
    }

    private fun handleAdvertisingCommand(command: CommunicationNearbyCommand.Advertising) {
        when (command) {
            is CommunicationNearbyCommand.Advertising.StartAdvertising -> {
            }
            is CommunicationNearbyCommand.Advertising.StopAdvertising -> {
            }
        }
    }



}