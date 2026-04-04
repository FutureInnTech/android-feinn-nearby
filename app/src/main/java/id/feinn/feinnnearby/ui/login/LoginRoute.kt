package id.feinn.feinnnearby.ui.login

import androidx.navigation3.runtime.EntryProviderScope
import id.feinn.feinnnearby.ui.NavScreen

fun EntryProviderScope<NavScreen>.loginScreen(
    onCreateAccountClick: () -> Unit = {}
) {
    entry<NavScreen.LoginScreen> {
        LoginScreen(
            onCreateAccountClick = onCreateAccountClick
        )
    }
}