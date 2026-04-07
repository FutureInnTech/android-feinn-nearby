package id.feinn.feinnnearby.ui.onboarding

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import id.feinn.feinnnearby.ui.NavScreen

fun EntryProviderScope<NavKey>.onboardingScreen(
    onConnectClick: () -> Unit = {}
) {
    entry<NavScreen.OnboardingScreen> {
        OnboardingScreen(
            onConnectClick = onConnectClick
        )
    }
}