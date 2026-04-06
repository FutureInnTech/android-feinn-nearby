package id.feinn.feinnnearby.ui.createAccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.feinn.feinnnearby.utils.CryptoUtils
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateAccountViewModel : ViewModel() {

    private val _state = MutableStateFlow(CreateAccountState())
    val state: StateFlow<CreateAccountState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CreateAccountEffect>()
    val effect: SharedFlow<CreateAccountEffect> = _effect.asSharedFlow()

    init {
        onIntent(CreateAccountIntent.LoadSeedPhrase)
    }

    fun onIntent(intent: CreateAccountIntent) {
        when (intent) {
            CreateAccountIntent.LoadSeedPhrase -> loadSeedPhrase()
            CreateAccountIntent.CopySeedPhrase -> copyToClipboard()
        }
    }

    private fun loadSeedPhrase() {
        val phrase = CryptoUtils.generateSeedPhrase()
        val words = phrase.split(" ")
        _state.update { it.copy(seedPhrase = words) }
    }

    private fun copyToClipboard() {
        val phrase = _state.value.seedPhrase.joinToString(" ")
        viewModelScope.launch {
            _effect.emit(CreateAccountEffect.CopySeedPhrase(phrase))
        }
    }
}
