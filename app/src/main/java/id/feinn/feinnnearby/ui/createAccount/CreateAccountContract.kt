package id.feinn.feinnnearby.ui.createAccount

data class CreateAccountState(
    val seedPhrase: List<String> = emptyList()
)

sealed interface CreateAccountIntent {
    data object LoadSeedPhrase : CreateAccountIntent
    data object CopySeedPhrase : CreateAccountIntent
}

sealed interface CreateAccountEffect {
    data class CopySeedPhrase(val phrase: String) : CreateAccountEffect
}
