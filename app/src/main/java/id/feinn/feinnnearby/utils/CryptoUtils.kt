package id.feinn.feinnnearby.utils

import org.web3j.crypto.MnemonicUtils
import java.security.SecureRandom

object CryptoUtils {

    /**
     * Generates a 12-word seed phrase (mnemonic) using 128 bits of entropy.
     */
    fun generateSeedPhrase(): String {
        val initialEntropy = ByteArray(16) // 128 bits = 12 words
        SecureRandom().nextBytes(initialEntropy)
        return MnemonicUtils.generateMnemonic(initialEntropy)
    }

}
