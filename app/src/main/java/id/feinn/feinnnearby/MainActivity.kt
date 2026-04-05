package id.feinn.feinnnearby

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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

        enableEdgeToEdge()

        setContent {
            FeinnNearbyTheme {
                val backstack by viewModel.backstackEntry.collectAsStateWithLifecycle()

                RootNavHost(
                    modifier = Modifier.fillMaxSize(),
                    onBack = viewModel::pop,
                    onPush = viewModel::push,
                    onReplaceAll = viewModel::replaceAll,
                    backStack = backstack
                )
            }
        }
    }

    private fun startService() {
        val communicationServiceIntent = Intent(this, CommunicationNearbyService::class.java)
        ContextCompat.startForegroundService(this, communicationServiceIntent)
        communicationNearbyManager.ensureBind()
    }

    override fun onDestroy() {
        communicationNearbyManager.doUnbind()
        super.onDestroy()
    }
}
