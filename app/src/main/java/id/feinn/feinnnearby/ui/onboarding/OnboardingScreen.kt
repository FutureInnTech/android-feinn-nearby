package id.feinn.feinnnearby.ui.onboarding

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import id.feinn.feinnnearby.ui.onboarding.page.HowItWorkPage
import id.feinn.feinnnearby.ui.onboarding.page.JoinMeshNetworkPage
import id.feinn.feinnnearby.ui.onboarding.page.PermissionEducationPage
import kotlinx.coroutines.launch

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun OnboardingScreen(
    onConnectClick: () -> Unit = {}
) {
    Scaffold { _ ->
        val pagerState = rememberPagerState(pageCount = { 3 })
        val coroutineScope = rememberCoroutineScope()

        val permissionsLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { _ ->
            coroutineScope.launch {
                pagerState.animateScrollToPage(2)
            }
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier.fillMaxSize()
        ) { page ->

            when (page) {
                0 -> HowItWorkPage(
                    onGetStartedClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    }
                )

                1 -> PermissionEducationPage(
                    onAllowAll = {
                        val permissions = mutableListOf<String>()

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissions.add(Manifest.permission.NEARBY_WIFI_DEVICES)
                            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            permissions.add(Manifest.permission.BLUETOOTH_SCAN)
                            permissions.add(Manifest.permission.BLUETOOTH_ADVERTISE)
                            permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
                        }

                        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
                        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)

                        permissionsLauncher.launch(permissions.toTypedArray())
                    },
                    onSetUpManually = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(2)
                        }
                    }
                )

                2 -> JoinMeshNetworkPage(
                    onConnectClick = onConnectClick
                )
            }

        }

    }
}
