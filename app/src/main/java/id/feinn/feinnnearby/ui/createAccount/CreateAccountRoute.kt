package id.feinn.feinnnearby.ui.createAccount

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import id.feinn.feinnnearby.ui.NavScreen
import org.koin.androidx.compose.koinViewModel

fun EntryProviderScope<NavKey>.createAccountScreen(
    onWrittenDownClicked: () -> Unit = {},
) {
    entry<NavScreen.CreateAccountScreen> {
        val viewModel: CreateAccountViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is CreateAccountEffect.CopySeedPhrase -> {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Seed Phrase", effect.phrase)
                        clipboard.setPrimaryClip(clip)
                    }
                }
            }
        }

        CreateAccountScreen(
            state = state,
            onWrittenDownClicked = onWrittenDownClicked,
            onCopyClicked = {
                viewModel.onIntent(CreateAccountIntent.CopySeedPhrase)
            }
        )
    }
}
