package id.feinn.feinnnearby.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
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
    onPush: (NavScreen) -> Unit = {},
    backStack: List<NavScreen>
) {

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = onBack,
        entryProvider = entryProvider {
            onboardingScreen(
                onConnectClick = {
                    onPush(NavScreen.LoginScreen)
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
                    onPush(NavScreen.DashboardScreen)
                }
            )
            dashboardScreen()

        }

    )


}