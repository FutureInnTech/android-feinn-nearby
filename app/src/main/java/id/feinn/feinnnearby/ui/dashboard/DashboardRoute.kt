package id.feinn.feinnnearby.ui.dashboard

import androidx.navigation3.runtime.EntryProviderScope
import id.feinn.feinnnearby.ui.NavScreen
import org.koin.androidx.compose.koinViewModel

fun EntryProviderScope<NavScreen>.dashboardScreen() {
    entry<NavScreen.DashboardScreen> {
        val viewModel: DashboardViewModel = koinViewModel()

        DashboardScreen()
    }
}
