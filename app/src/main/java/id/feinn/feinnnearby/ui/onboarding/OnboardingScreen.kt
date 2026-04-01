package id.feinn.feinnnearby.ui.onboarding

import android.annotation.SuppressLint
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
    Scaffold {  _ ->

        val pagerState = rememberPagerState(pageCount = { 3 })
        val coroutineScope = rememberCoroutineScope()

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier.fillMaxSize()
        ) { page ->

            when(page) {
                0 -> HowItWorkPage(
                    onGetStartedClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    }
                )
                1 -> PermissionEducationPage(
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