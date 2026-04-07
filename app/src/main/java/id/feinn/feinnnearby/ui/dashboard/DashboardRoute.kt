package id.feinn.feinnnearby.ui.dashboard

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import id.feinn.feinnnearby.ui.NavScreen
import org.koin.androidx.compose.koinViewModel

fun EntryProviderScope<NavKey>.dashboardScreen() {
    entry<NavScreen.DashboardScreen> {
        val viewModel: DashboardViewModel = koinViewModel()

        DashboardScreen()
    }
}
