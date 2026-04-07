package id.feinn.feinnnearby.ui

import androidx.lifecycle.ViewModel
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NavigationViewModel : ViewModel() {

    private val _backstackEntry = MutableStateFlow(listOf<NavKey>(NavScreen.OnboardingScreen))
    val backstackEntry: StateFlow<List<NavKey>> = _backstackEntry

    fun push(screen: NavKey) {
        _backstackEntry.update { it + screen }
    }

    fun replaceAll(screen: NavKey) {
        _backstackEntry.update { listOf(screen) }
    }

    fun pop() {
        _backstackEntry.update { list ->
            if (list.isNotEmpty()) list.dropLast(1) else list
        }
    }

}
