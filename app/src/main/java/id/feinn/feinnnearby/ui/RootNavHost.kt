package id.feinn.feinnnearby.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import id.feinn.feinnnearby.ui.createAccount.createAccountScreen
import id.feinn.feinnnearby.ui.dashboard.dashboardScreen
import id.feinn.feinnnearby.ui.login.loginScreen
import id.feinn.feinnnearby.ui.onboarding.onboardingScreen
import id.feinn.feinnnearby.ui.profileSetup.profileSetupScreen

@Composable
fun RootNavHost(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onPush: (NavKey) -> Unit = {},
    onReplaceAll: (NavKey) -> Unit = {},
    backStack: () -> List<NavKey> = { emptyList() }
) {

    NavDisplay(
        modifier = modifier,
        backStack = backStack(),
        onBack = onBack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            onboardingScreen(
                onConnectClick = {
                    onReplaceAll(NavScreen.LoginScreen)
                }
            )
            loginScreen(
                onCreateAccountClick = {
                    onPush(NavScreen.CreateAccountScreen)
                }
            )
            createAccountScreen(
                onWrittenDownClicked = {
                    onPush(NavScreen.ProfileSetupScreen)
                }
            )
            profileSetupScreen(
                onEnterDashboard = {
                    onReplaceAll(NavScreen.DashboardScreen)
                }
            )
            dashboardScreen()

        }

    )


}
