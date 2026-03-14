package id.feinn.feinnnearby.ui


import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface NavScreen : NavKey {

    @Serializable
    data object DashboardScreen: NavScreen

}