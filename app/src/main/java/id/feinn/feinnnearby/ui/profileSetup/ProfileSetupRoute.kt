package id.feinn.feinnnearby.ui.profileSetup

import androidx.navigation3.runtime.EntryProviderScope
import id.feinn.feinnnearby.ui.NavScreen

fun EntryProviderScope<NavScreen>.profileSetupScreen() {
    entry<NavScreen.ProfileSetupScreen> {

        ProfileSetupScreen()
    }
}
