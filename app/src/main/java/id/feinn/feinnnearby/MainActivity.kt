package id.feinn.feinnnearby

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.feinn.feinnnearby.data.service.communication.CommunicationNearbyManager
import id.feinn.feinnnearby.data.service.communication.CommunicationNearbyService
import id.feinn.feinnnearby.ui.NavigationViewModel
import id.feinn.feinnnearby.ui.RootNavHost
import id.feinn.feinnnearby.ui.theme.FeinnNearbyTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: NavigationViewModel by viewModel()
    private val communicationNearbyManager: CommunicationNearbyManager by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startService()

        enableEdgeToEdge()

        setContent {
            FeinnNearbyTheme {
                val backstack by viewModel.backstackEntry.collectAsStateWithLifecycle()

                RootNavHost(
                    modifier = Modifier.fillMaxSize(),
                    backStack = backstack,
                    onBack = viewModel::pop
                )
            }
        }
    }

    private fun startService() {
        val communicationServiceIntent = Intent(this, CommunicationNearbyService::class.java)
        ContextCompat.startForegroundService(this, communicationServiceIntent)
        communicationNearbyManager.doBind()
    }

    override fun onDestroy() {
        communicationNearbyManager.doUnbind()
        super.onDestroy()
    }

    private fun ensureNearbyPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.NEARBY_WIFI_DEVICES)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.NEARBY_WIFI_DEVICES),
                    1001
                )
            }
        } else {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1002
                )
            }
        }
    }
}