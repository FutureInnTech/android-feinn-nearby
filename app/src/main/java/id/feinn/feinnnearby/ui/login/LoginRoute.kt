package id.feinn.feinnnearby.ui.login

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import id.feinn.feinnnearby.ui.NavScreen

fun EntryProviderScope<NavKey>.loginScreen(
    onCreateAccountClick: () -> Unit = {}
) {
    entry<NavScreen.LoginScreen> {
        LoginScreen(
            onCreateAccountClick = onCreateAccountClick
        )
    }
}