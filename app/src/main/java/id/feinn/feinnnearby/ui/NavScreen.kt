package id.feinn.feinnnearby.ui


import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface NavScreen : NavKey {

    @Serializable
    data object OnboardingScreen: NavScreen

    @Serializable
    data object LoginScreen: NavScreen

    @Serializable
    data object CreateAccountScreen: NavScreen

    @Serializable
    data object ProfileSetupScreen: NavScreen

    @Serializable
    data object DashboardScreen: NavScreen

}