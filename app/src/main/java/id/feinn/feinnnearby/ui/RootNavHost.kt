package id.feinn.feinnnearby.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import id.feinn.feinnnearby.ui.dashboard.dashboardScreen

@Composable
fun RootNavHost(
    modifier: Modifier = Modifier,
    backStack: List<NavScreen>,
    onBack: () -> Unit
) {

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = onBack,
        entryProvider = entryProvider {
            dashboardScreen()
        }

    )


}