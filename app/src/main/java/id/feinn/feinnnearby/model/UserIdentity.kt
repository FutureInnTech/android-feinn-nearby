package id.feinn.feinnnearby.model

data class UserIdentity(
    val seedPhrase: String,
    val publicKey: String,
    val privateKey: String,
    val address: String
)
