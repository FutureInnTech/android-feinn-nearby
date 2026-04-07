package id.feinn.feinnnearby.ui.profileSetup

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import id.feinn.feinnnearby.ui.NavScreen

fun EntryProviderScope<NavKey>.profileSetupScreen(
    onEnterDashboard: () -> Unit = {}
) {
    entry<NavScreen.ProfileSetupScreen> {

        ProfileSetupScreen(
            onEnterDashboard = onEnterDashboard
        )
    }
}
