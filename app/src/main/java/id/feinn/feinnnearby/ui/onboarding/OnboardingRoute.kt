package id.feinn.feinnnearby.ui.onboarding

import androidx.navigation3.runtime.EntryProviderScope
import id.feinn.feinnnearby.ui.NavScreen

fun EntryProviderScope<NavScreen>.onboardingScreen() {
    entry<NavScreen.OnboardingScreen> {
        OnboardingScreen()
    }
}