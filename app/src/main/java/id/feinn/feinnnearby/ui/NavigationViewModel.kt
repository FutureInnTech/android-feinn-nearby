package id.feinn.feinnnearby.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NavigationViewModel : ViewModel() {

    private val _backstackEntry = MutableStateFlow(listOf<NavScreen>(NavScreen.OnboardingScreen))
    val backstackEntry: StateFlow<List<NavScreen>> = _backstackEntry

    fun push(screen: NavScreen) {
        _backstackEntry.update { it + screen }
    }

    fun pop() {
        _backstackEntry.update { list ->
            if (list.isNotEmpty()) list.dropLast(1) else list
        }
    }

}